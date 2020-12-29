package com.driftbapo.driftbapomod.common.classes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class BrianAxe extends AxeItem {

    private int counter;
    private static final int BLOCK_BREAK_LIMIT = 1000;

    public BrianAxe (IItemTier itier, int ad, float atkspd, Properties builder) {
        super(itier, ad, atkspd, builder);
        counter = 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn) {
        counter = 0;
        ItemStack itemStackIn = p.getHeldItem(handIn);
        BlockRayTraceResult rayTraceResult = this.rayTrace(worldIn, p, RayTraceContext.FluidMode.NONE);
        BlockPos origin = rayTraceResult.getPos();

        helper_function(worldIn, p, handIn, origin);

        return ActionResult.resultPass(itemStackIn);
    }

    private ActionResult<ItemStack> helper_function(World worldIn, PlayerEntity p, Hand handIn, BlockPos origin) {
        ItemStack itemStackIn = p.getHeldItem(handIn);

        if (isTree(worldIn, origin)) {
            counter++;
            if (counter > BLOCK_BREAK_LIMIT) {
                return ActionResult.resultFail(itemStackIn);
            }
            worldIn.destroyBlock(origin, true);
            helper_function(worldIn, p, handIn, origin.add(1, 0, 0));
            helper_function(worldIn, p, handIn, origin.add(-1, 0, 0));
            helper_function(worldIn, p, handIn, origin.add(0, 1, 0));
            helper_function(worldIn, p, handIn, origin.add(0, -1, 0));
            helper_function(worldIn, p, handIn, origin.add(0, 0, 1));
            helper_function(worldIn, p, handIn, origin.add(0, 0, -1));
        }

        return ActionResult.resultPass(itemStackIn);
    }

    private boolean isTree(World worldIn, BlockPos origin) {
        ResourceLocation logsID = new ResourceLocation("minecraft", "logs");
        ResourceLocation leavesID = new ResourceLocation("minecraft", "leaves");

        if (BlockTags.getCollection().getTagByID(logsID).contains(worldIn.getBlockState(origin).getBlock())
            || BlockTags.getCollection().getTagByID(leavesID).contains(worldIn.getBlockState(origin).getBlock()))
            return true;

        return false;
    }

    private static boolean isTreeOld(World worldIn, BlockPos origin) {
        if(worldIn.getBlockState(origin).getBlock().equals(Blocks.STRIPPED_OAK_LOG) || worldIn.getBlockState(origin).getBlock().equals(Blocks.OAK_LOG)
            || worldIn.getBlockState(origin).getBlock().equals(Blocks.STRIPPED_BIRCH_LOG) || worldIn.getBlockState(origin).getBlock().equals(Blocks.BIRCH_LOG)
            || worldIn.getBlockState(origin).getBlock().equals(Blocks.STRIPPED_ACACIA_LOG) || worldIn.getBlockState(origin).getBlock().equals(Blocks.ACACIA_LOG)
            || worldIn.getBlockState(origin).getBlock().equals(Blocks.STRIPPED_DARK_OAK_LOG) || worldIn.getBlockState(origin).getBlock().equals(Blocks.DARK_OAK_LOG)
            || worldIn.getBlockState(origin).getBlock().equals(Blocks.STRIPPED_JUNGLE_LOG) || worldIn.getBlockState(origin).getBlock().equals(Blocks.JUNGLE_LOG)
            || worldIn.getBlockState(origin).getBlock().equals(Blocks.STRIPPED_SPRUCE_LOG) || worldIn.getBlockState(origin).getBlock().equals(Blocks.SPRUCE_LOG)
            || worldIn.getBlockState(origin).getBlock().equals(Blocks.OAK_LEAVES) || worldIn.getBlockState(origin).getBlock().equals(Blocks.DARK_OAK_LEAVES)
            || worldIn.getBlockState(origin).getBlock().equals(Blocks.SPRUCE_LEAVES) || worldIn.getBlockState(origin).getBlock().equals(Blocks.ACACIA_LEAVES)
            || worldIn.getBlockState(origin).getBlock().equals(Blocks.JUNGLE_LEAVES) || worldIn.getBlockState(origin).getBlock().equals(Blocks.BIRCH_LEAVES)){
            return true;
        }
        return false;
    }

}