package erebus.world.biomes.decorators;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import erebus.ModBlocks;
import erebus.block.BlockLeavesErebus;
import erebus.block.BlockLogErebus;
import erebus.world.feature.decoration.WorldGenPonds;
import erebus.world.feature.decoration.WorldGenQuickSand;
import erebus.world.feature.plant.WorldGenBamboo;
import erebus.world.feature.plant.WorldGenMelon;
import erebus.world.feature.plant.WorldGenTurnips;
import erebus.world.feature.structure.WorldGenWaspDungeon;
import erebus.world.feature.tree.WorldGenAsperTree;
import erebus.world.feature.tree.WorldGenErebusHugeTree;
import erebus.world.feature.tree.WorldGenErebusTrees;
import erebus.world.feature.tree.WorldGenEucalyptusTree;
import erebus.world.feature.tree.WorldGenMossbarkTree;
import erebus.world.feature.tree.WorldGenTallJungleTree;

public class BiomeDecoratorUndergroundJungle extends BiomeDecoratorErebus{
	private final WorldGenWaspDungeon genWaspDungeon = new WorldGenWaspDungeon();
	private final WorldGenQuickSand genQuickSand = new WorldGenQuickSand();
	private final WorldGenPonds genPonds = new WorldGenPonds();
	
	private final WorldGenFlowers genMushroomsBrown = new WorldGenFlowers(Block.mushroomBrown.blockID);
	private final WorldGenFlowers genMushroomsRed = new WorldGenFlowers(Block.mushroomRed.blockID);
	private final WorldGenBigMushroom genBigMushroomRed = new WorldGenBigMushroom(0);
	private final WorldGenBigMushroom genBigMushroomBrown = new WorldGenBigMushroom(1);
	
	private final WorldGenTallGrass genFerns = new WorldGenTallGrass(ModBlocks.fern.blockID,1);
	private final WorldGenTallGrass genFiddleheads = new WorldGenTallGrass(ModBlocks.fiddlehead.blockID,1);
	private final WorldGenTallGrass genGrass = new WorldGenTallGrass(ModBlocks.erebusGrass.blockID,1);
	
	private final WorldGenerator genTreeMahogany = new WorldGenErebusTrees(true,5,BlockLogErebus.dataMahogany,BlockLeavesErebus.dataMahoganyDecay,false,ModBlocks.logErebusGroup1.blockID,ModBlocks.leavesErebus.blockID,ModBlocks.thorns.blockID);
	private final WorldGenerator genTreeMahoganyLarge = new WorldGenErebusHugeTree(true,BlockLogErebus.dataMahogany,BlockLeavesErebus.dataMahoganyDecay,false,ModBlocks.logErebusGroup1.blockID,ModBlocks.leavesErebus.blockID);
	private final WorldGenerator genTreeJungle = new WorldGenTrees(true,6,3,3,true);
	//private final WorldGenerator genTreeJungleLarge = new WorldGenHugeTrees(true,4+rand.nextInt(40),3,3);
	private final WorldGenerator genTreeMossbark = new WorldGenMossbarkTree();
	private final WorldGenerator genTreeAsper = new WorldGenAsperTree();
	private final WorldGenerator genTreeJungleTall = new WorldGenTallJungleTree();
	private final WorldGenerator genTreeEucalyptus = new WorldGenEucalyptusTree();
	
	private final WorldGenBamboo genBamboo = new WorldGenBamboo(13,false);
	private final WorldGenTurnips genTurnips = new WorldGenTurnips();
	private final WorldGenMelon genMelons = new WorldGenMelon();
	
	@Override
	protected void generateBiomeFeatures(){
		for(attempt = 0; attempt < 20; attempt++){
			xx = x+offsetXZ();
			yy = rand.nextInt(120);
			zz = z+offsetXZ();

			if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
				genPonds.prepare((rand.nextDouble()+0.7D)*1.5D);
				genPonds.generate(world,rand,xx,yy,zz);
			}
		}

		if (rand.nextInt(37) == 0){
			for(attempt = 0; attempt < 5; attempt++){
				if (genWaspDungeon.generate(world,rand,x+offsetXZ(),127,z+offsetXZ())) break;
			}
		}

		for(attempt = 0; attempt < 10; attempt++){
			xx = x+offsetXZ();
			yy = rand.nextInt(120);
			zz = z+offsetXZ();

			if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
				genQuickSand.generate(world,rand,xx,yy,zz);
			}
		}

		for(attempt = 0; attempt < 2200; attempt++){
			xx = x+offsetXZ();
			yy = 15+rand.nextInt(90);
			zz = z+offsetXZ();

			if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
				WorldGenerator treeGen = null;
				int r = rand.nextInt(31);

				if (r <= 6)treeGen = new WorldGenHugeTrees(true,4+rand.nextInt(40),3,3); // TODO
				else if (r <= 11)treeGen = genTreeMahogany;
				else if (r <= 16){
					((WorldGenErebusHugeTree)genTreeMahoganyLarge).prepare(20+rand.nextInt(5));
					treeGen = genTreeMahoganyLarge;
				}
				else if (r <= 20)treeGen = genTreeAsper;
				else if (r <= 23)treeGen = genTreeJungle;
				else if (r <= 26)treeGen = genTreeMossbark;
				else if (r <= 28)treeGen = genTreeJungleTall;
				else treeGen = genTreeEucalyptus;

				treeGen.generate(world,rand,xx,yy,zz);
			}
		}

		genMushroomsBrown.generate(world,rand,x+offsetXZ(),rand.nextInt(128),z+offsetXZ());
		genMushroomsRed.generate(world,rand,x+offsetXZ(),rand.nextInt(128),z+offsetXZ());

		for(attempt = 0; attempt < 12; attempt++){
			xx = x+offsetXZ();
			yy = 15+rand.nextInt(90);
			zz = z+offsetXZ();

			if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
				genBigMushroomRed.generate(world,rand,xx,yy,zz);
			}
		}

		for(attempt = 0; attempt < 20; attempt++){
			xx = x+offsetXZ();
			yy = 15+rand.nextInt(90);
			zz = z+offsetXZ();

			if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
				genBigMushroomBrown.generate(world,rand,xx,yy,zz);
			}
		}

		if (rand.nextInt(11) == 0){
			xx = x+offsetXZ();
			zz = z+offsetXZ();
			
			for(yy = 90; yy > 20; yy--){
				if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
					if (genBamboo.generate(world,rand,xx,yy,zz))break;
				}
			}
		}

		for(attempt = 0; attempt < 40; attempt++){
			xx = x+offsetXZ();
			yy = 15+rand.nextInt(90);
			zz = z+offsetXZ();

			if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
				genFerns.generate(world,rand,xx,yy,zz);
			}
		}

		for(attempt = 0; attempt<16; attempt++){
			xx = x+offsetXZ();
			yy = 15+rand.nextInt(90);
			zz = z+offsetXZ();

			if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
				genFiddleheads.generate(world,rand,xx,yy,zz);
			}
		}

		for(attempt = 0; attempt < 900; attempt++){
			xx = x+offsetXZ();
			yy = 15+rand.nextInt(90);
			zz = z+offsetXZ();

			if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
				genGrass.generate(world,rand,xx,yy,zz);
			}
		}

		int block,offset;
		for(attempt = 0; attempt < 800; attempt++){
			xx = x+offsetXZ();
			yy = 30+rand.nextInt(80);
			zz = z+offsetXZ();

			if (world.isAirBlock(xx,yy,zz)){
				block = rand.nextBoolean() ? Block.vine.blockID : ModBlocks.thorns.blockID;
				offset = rand.nextInt(4);

				if (!Block.isNormalCube(world.getBlockId(xx+Direction.offsetX[offset],yy,zz+Direction.offsetZ[offset]))) continue;

				for(int vineY = rand.nextInt(30); vineY > 0; vineY--){
					if (world.getBlockId(xx+Direction.offsetX[offset],yy-vineY,zz+Direction.offsetZ[offset]) == 0){
						world.setBlock(xx+Direction.offsetX[offset],yy-vineY,zz+Direction.offsetZ[offset],Block.vine.blockID,(offset==3 ? 1 : offset==2 ? 4 : offset==1 ? 0 : 2),3);
					}
				}
			}
		}

		if (rand.nextInt(3) == 0){
			for(attempt = 0; attempt < 6; ++attempt){
				xx = x+offsetXZ();
				yy = 15+rand.nextInt(90);
				zz = z+offsetXZ();

				if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
					genTurnips.generate(world,rand,xx,yy,zz);
				}
			}
		}
		else if (rand.nextBoolean() || rand.nextBoolean()){
			for(attempt = 0; attempt < 3; ++attempt){
				xx = x+offsetXZ();
				yy = 15+rand.nextInt(90);
				zz = z+offsetXZ();

				if (world.getBlockId(xx,yy-1,zz) == Block.grass.blockID && world.isAirBlock(xx,yy,zz)){
					genMelons.generate(world,rand,xx,yy,zz);
				}
			}
		}
	}
}
