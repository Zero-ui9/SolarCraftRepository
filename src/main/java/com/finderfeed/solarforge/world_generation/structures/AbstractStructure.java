package com.finderfeed.solarforge.world_generation.structures;

import com.finderfeed.solarforge.world_generation.structures.dungeon_one_key_lock.DungeonOnePieces;
import com.finderfeed.solarforge.world_generation.structures.maze_key_keeper.MazeStructure;
import com.finderfeed.solarforge.world_generation.structures.maze_key_keeper.MazeStructurePieces;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;



public class AbstractStructure extends StructureFeature<NoneFeatureConfiguration> {


    public AbstractStructure(Codec<NoneFeatureConfiguration> p_197168_) {
        super(p_197168_, PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), AbstractStructure::generatePieces));
    }

    private static void generatePieces(StructurePiecesBuilder p_197089_, PieceGenerator.Context<NoneFeatureConfiguration> ctx) {
        int x = (ctx.chunkPos().x << 4) + 7;
        int z = (ctx.chunkPos().z << 4) + 7;
        int surfaceY = ctx.chunkGenerator().getBaseHeight(x,z, Heightmap.Types.WORLD_SURFACE_WG,ctx.heightAccessor());
        BlockPos blockpos = new BlockPos(x, surfaceY, z);
        Rotation rotation = Rotation.getRandom(ctx.random());
        DungeonOnePieces.start(ctx.structureManager(), blockpos, rotation, p_197089_, ctx.random());
    }
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
//    public AbstractStructure(Codec<NoneFeatureConfiguration> codec){
//        super(codec);
//    }

//    @Override
//    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
//        return AbstractStructure.Start::new;
//    }
//
//    @Override
//    public GenerationStep.Decoration step() {
//        return GenerationStep.Decoration.SURFACE_STRUCTURES;
//    }
//
//
//
//
//    @Override
//    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, WorldgenRandom chunkRandom, ChunkPos pos, Biome biome, ChunkPos chunkPos, NoneFeatureConfiguration featureConfig,LevelHeightAccessor p_160463_) {
//        BlockPos centerOfChunk = new BlockPos((pos.x << 4) + 7, 0, (pos.z << 4) + 7);
//        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG,p_160463_);
//        NoiseColumn columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(),p_160463_);
//        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
//        return topBlock.getFluidState().isEmpty();
//    }
//
//
//    public static class Start extends StructureStart<NoneFeatureConfiguration>{
//
//
//        public Start(StructureFeature<NoneFeatureConfiguration> p_163595_, ChunkPos p_163596_, int p_163597_, long p_163598_) {
//            super(p_163595_, p_163596_, p_163597_, p_163598_);
//        }
//
//        @Override
//        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManagerIn, ChunkPos pos, Biome biomeIn, NoneFeatureConfiguration config,LevelHeightAccessor p_163621_) {
//            Rotation rotation = Rotation.values()[this.random.nextInt(Rotation.values().length)];
//
//            // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
//            int x = (pos.x << 4) + 7;
//            int z = (pos.z << 4) + 7;
//
//            // Finds the y value of the terrain at location.
//            int surfaceY = chunkGenerator.getBaseHeight(pos.x, pos.z, Heightmap.Types.WORLD_SURFACE_WG,p_163621_);
//            BlockPos blockpos = new BlockPos(x, surfaceY, z);
//
//            // Now adds the structure pieces to this.components with all details such as where each part goes
//            // so that the structure can be added to the world by worldgen.
//            DungeonOnePieces.start(templateManagerIn, blockpos, rotation, this.pieces, this.random);
//
//            // Sets the bounds of the structure.
//
//
//            // I use to debug and quickly find out if the structure is spawning or not and where it is.
//            //SolarForge.LOGGER.log(Level.DEBUG, "Structure at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
//        }
//
//
//    }

}

