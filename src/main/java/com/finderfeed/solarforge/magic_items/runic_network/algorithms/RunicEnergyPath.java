package com.finderfeed.solarforge.magic_items.runic_network.algorithms;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy.RunicEnergyGiver;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.BaseRepeaterTile;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunicEnergyPath {

    private final RunicEnergy.Type type;
    private List<BlockPos> FINAL_POSITIONS = new ArrayList<>();


    public RunicEnergyPath(RunicEnergy.Type type){
        this.type = type;
    }

    @Nullable
    public List<BlockPos> build(BaseRepeaterTile beggining){
        Map<BlockPos,List<BlockPos>> graph = buildGraph(beggining,new ArrayList<>(),new HashMap<>());
        if (hasEndPoint()){
            sortBestGiver(beggining.getLevel());
            return buildRouteAStar(graph,beggining.getBlockPos(),beggining.getLevel());
        }else{
            return null;
        }
    }



    private Map<BlockPos,List<BlockPos>> buildGraph(BaseRepeaterTile tile,List<BlockPos> visited,Map<BlockPos,List<BlockPos>> toReturn){
        List<BlockPos> positions = findConnectablePylonsAndEnergySources(tile,tile.getMaxRange());
        toReturn.put(tile.getBlockPos(),positions);
        visited.add(tile.getBlockPos());
        positions.forEach((pos)->{
            if (!visited.contains(pos) ){
                BaseRepeaterTile rep = getTile(pos,tile.getLevel());
                if (rep != null) {
                    buildGraph(rep, visited, toReturn);
                }
            }
        });

        return toReturn;
    }




    private BaseRepeaterTile getTile(BlockPos pos,Level w){
        BlockEntity be = w.getBlockEntity(pos);
        return  be instanceof BaseRepeaterTile ? (BaseRepeaterTile) be : null;
    }


    private List<BlockPos> findConnectablePylonsAndEnergySources(BaseRepeaterTile start, double range){
        Level world = start.getLevel();
        BlockPos mainpos = start.getBlockPos();
        List<LevelChunk> chunks = Helpers.getSurroundingChunks5Radius(mainpos,world);

        List<BlockPos> tiles = new ArrayList<>();
        for (LevelChunk chunk : chunks){
            chunk.getBlockEntities().forEach((position,tileentity)->{
                if ((tileentity instanceof BaseRepeaterTile repeater)
                        && !(Helpers.equalsBlockPos(start.getBlockPos(),position))
                        && (repeater.getEnergyType() == start.getEnergyType())){
                    if (FinderfeedMathHelper.canSeeTileEntity(start,repeater,range)){
                        tiles.add(tileentity.getBlockPos());
                    }
                }else if ((tileentity instanceof RunicEnergyGiver giver) &&
                        (giver.getTypes() != null) &&
                        (giver.getTypes().contains(start.getEnergyType())) &&
                        (FinderfeedMathHelper.getDistanceBetween(start.getBlockPos(),giver.getPos()) <= range)){
//                    tiles.add(giver.getPos());
                    if (!FINAL_POSITIONS.contains(giver.getPos())) {
                        FINAL_POSITIONS.add(giver.getPos());
                    }
                }
            });
        }
        return tiles;
    }

    private boolean hasEndPoint(){
        return !FINAL_POSITIONS.isEmpty();
    }

    private void sortBestGiver(Level w){
        FINAL_POSITIONS.sort((n1,n2)->{
            return (int)Math.round(gv(w,n1).getRunicEnergy(type)) - (int)Math.round(gv(w,n2).getRunicEnergy(type));
        });
        BlockPos pos = FINAL_POSITIONS.get(0);
        FINAL_POSITIONS.clear();
        FINAL_POSITIONS.add(pos);
    }

    private RunicEnergyGiver gv(Level w,BlockPos pos){
        return (RunicEnergyGiver) w.getBlockEntity(pos);
    }


    private Node findLeastFNode(List<Node> nodes){
        int minindex = -1;
        double minf = 10000000;
        for (int i = 0; i < nodes.size();i++){
            if (nodes.get(i).f < minf){
                minf = nodes.get(i).f;
                minindex = i;
            }
        }
        return nodes.get(minindex);
    }

    private List<BlockPos> buildRouteAStar(Map<BlockPos,List<BlockPos>> pylons,BlockPos start,Level world){
        BlockPos finalPos = FINAL_POSITIONS.get(0);

//        for (BlockPos pos : pylons.keySet()){
//            if (getTile(pos,world).hasConnection()){
//                finalPos = pos;
//                break;
//            }
//        }

        List<BlockPos> alreadyVisited = new ArrayList<>();
        alreadyVisited.add(start);
        List<Node> hold = new ArrayList<>();
        List<Node> open = new ArrayList<>();
        Node currentNode = new Node(start,finalPos,0);

        if (!Helpers.equalsBlockPos(start,finalPos)) {

            while (true) {
                List<BlockPos> nodes = pylons.get(currentNode.pos);
                for (int i = 0; i < nodes.size(); i++) {
                    if (!alreadyVisited.contains(nodes.get(i))) {
                        alreadyVisited.add(nodes.get(i));
                        Node nd = new Node(nodes.get(i), finalPos, currentNode.g + FinderfeedMathHelper.getDistanceBetween(nodes.get(i), currentNode.pos));
                        nd.setSavedPath(new ArrayList<>(currentNode.getSavedPath()));
                        nd.addToPath(currentNode.pos);
                        open.add(nd);
                    }
                }
                if (!open.isEmpty()) {
                    Node leastF = findLeastFNode(open);
                    open.forEach((node) -> {
                        if (!node.equals(leastF)) {
                            hold.add(node);
                        }
                    });
                    open.clear();
                    currentNode = leastF;
                    //TODO:final position SHOULD BE A REPEATER NOT AN ENERGY GIVER
                    if (Helpers.equalsBlockPos(finalPos, currentNode.pos)) {
                        currentNode.addToPath(finalPos);
                        break;
                    }
                } else {
                    Node leastF = findLeastFNode(hold);
                    hold.remove(leastF);
                    currentNode = leastF;

                    if (Helpers.equalsBlockPos(finalPos, currentNode.pos)) {
                        currentNode.addToPath(finalPos);
                        break;
                    }
                }
            }
            List<BlockPos> savedPath = currentNode.getSavedPath();
//            savedPath.add(getTile(finalPos,world).getFinalPos());
            return savedPath;
        }else{
            return List.of(finalPos/*,getTile(finalPos,world).getFinalPos()*/);
        }

    }
}

class Node{

    public BlockPos pos;
    public double f;
    public double g;
    public double heuristic;
    private List<BlockPos> savedPath = new ArrayList<>();

    public Node(BlockPos pos,BlockPos finalPos,double g){
        this.pos = pos;
        this.heuristic = FinderfeedMathHelper.getDistanceBetween(pos,finalPos);
        this.g = g;
        this.f = heuristic+g;
    }

    public void setSavedPath(List<BlockPos> savedPath) {
        this.savedPath = savedPath;
    }

    public List<BlockPos> getSavedPath() {
        return savedPath;
    }

    public void addToPath(BlockPos pos){
        savedPath.add(pos);
    }
}
//                if ((tileentity instanceof BaseRepeaterTile repeater) && !Helpers.equalsBlockPos(tile.getBlockPos(),position) && (repeater.getEnergyType() == tile.getEnergyType())){
//                    if (FinderfeedMathHelper.canSeeTileEntity(repeater,tile,range)){
//                        tiles.add(repeater.getBlockPos());
//                    }
//                }else if (tileentity instanceof RunicEnergyGiver pylon){
//                    if ((pylon.getTypes() != null) && pylon.getTypes().contains(tile.getEnergyType()) && FinderfeedMathHelper.canSeeTileEntity(pylon.getPos(),tile.getBlockPos(),range,world)){
//                        if (tile.getFinalPos() == null){
//                            tile.setFinalPos(pylon.getBlockPos());
//                        }else{
//                            if (FinderfeedMathHelper.getDistanceBetween(pylon.getPos(),tile.getBlockPos()) < FinderfeedMathHelper.getDistanceBetween(tile.getBlockPos(),tile.getFinalPos())){
//                                tile.setFinalPos(pylon.getBlockPos());
//                            }
//                        }
//                    }
//                }