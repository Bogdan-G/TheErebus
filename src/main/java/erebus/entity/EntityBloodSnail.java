package erebus.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import erebus.ModItems;

public class EntityBloodSnail extends EntityMob {

	public EntityBloodSnail(World world) {
		super(world);
		setSize(1.0F, 0.8F);
		stepHeight = 0.0F;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D); // Movespeed
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D); // MaxHealth
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D); // atkDmg
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D); // followRange
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 3;
	}

	@Override
	protected String getLivingSound() {
		return "erebus:snailliving";
	}

	@Override
	protected String getHurtSound() {
		return "erebus:snailhurt";
	}

	@Override
	protected String getDeathSound() {
		return "erebus:snaildeath";
	}

	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		if (recentlyHit) {
			int chance = rand.nextInt(4) + rand.nextInt(1 + looting);
			int amount;
			for (amount = 0; amount < chance; ++amount)
				entityDropItem(new ItemStack(ModItems.lifeBlood), 0.0F);
		}
	}

	@Override
	protected Entity findPlayerToAttack() {
		EntityPlayer var1 = worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
		return var1 != null && canEntityBeSeen(var1) ? var1 : null;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (super.attackEntityAsMob(entity)) {
			if (entity instanceof EntityLiving) {
				byte duration = 0;
				if (worldObj.difficultySetting == EnumDifficulty.NORMAL)
					duration = 7;
				else if (worldObj.difficultySetting == EnumDifficulty.HARD)
					duration = 15;

				if (duration > 0) {
					((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
					((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.confusion.id, duration * 20, 0));
				}
			}
			return true;
		} else
			return false;
	}
}
