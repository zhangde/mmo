package com.tongwan.common.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** A* 寻路算法 */	
public class AStar {
	//是否�?��障碍物判�?
	public boolean isBalk = false;
	
	//====================================
	//	Constants
	//====================================
	public static final int COST_STRAIGHT = 10;		//竖向或斜向移动一格的路径评分
	public static final int COST_DIAGONAL = 20;		//横向移动�?��的路径评�?
	public static final int COST_SWERVE = 5;		//转弯�?��评分
	public static final int NOTE_ID = 0;			//(单个)节点数组 节点ID 索引
	public static final int NOTE_OPEN = 1;			//(单个)节点数组 是否在开启列表中 索引
	public static final int NOTE_CLOSED = 2;		//(单个)节点数组 是否在关闭列表中 索引
	private final byte[][] data;					//地图掩码
	//====================================
	//	Member Variables
	//====================================
	// �?��列表 m_openList 是个二叉堆（�?��数组），F值最小的节点始终排在�?��。为加快排序�?
	// �?��列表中只存放节点ID ，其它数据放在各自的�?��数组中�?
//	private Object m_mapTileModel;					//地图模型
	private List<Integer> m_openList;				//�?��列表，存放节点ID
	private int m_openCount;						//�?��列表长度
	private int m_openId;							//节点加入�?��列表时分配的唯一ID(�?�?��) 根据此ID(从下面的列表�?存取节点数据
	
	// 这些数据列表都以节点ID为索引顺序存储�?
//	private List<Integer> m_xList;					//节点x坐标列表
//	private List<Integer> m_yList;					//节点y坐标列表
	private List<Point>   m_List;					//节点x,y坐标列表
	private List<Integer> m_pathScoreList;			//节点路径评分列表
	private List<Integer> m_movementCostList;		//(从起点移动到)节点的移动�?费列�?
	private List<Integer> m_fatherList;				//节点的父节点(ID)列表
	
	// 使用 m_noteMap 可以方便的存取任何位置节点的�?��关闭状�?，并可取其ID进�?存取其它数据。m_noteMap 是个三维数组�?
	// 第一维y坐标（第几行），第二维x坐标（第几列），第三维节点状态和ID。判断点(p_x, p_y)是否在开启列表中
	private Map<Point, Node> m_noteMap;				//节点(数组)地图,根据节点坐标记录节点�?��关闭状�?和ID
	
	private int m_maxTry;							//�?��寻延�?限制超时返回
//	private int p_maxTry = 3000;

	//====================================
	//	Constructor
	//====================================
	/**
	 * Constructor
	 *
	 * @param p_mapTileModel	地图模型，实�?IMapTileModel 接口
	 * @param p_maxTry			�?��寻路步数，限制超时返�?
	 */
	public AStar(byte[][] data, int p_maxTry)
	{
		this.data 		= data;
		this.m_maxTry	= p_maxTry;
	}

	//====================================
	//	Properties
	//====================================
	/**
	 * �?��寻路步数，限制超时返�?
	 */
	public int getMaxTry()
	{
		return this.m_maxTry;
	}

	/**
	 * @private
	 */
	public void setMaxTry(int p_value)
	{
		this.m_maxTry = p_value;
	}

	//====================================
	//	Public Methods
	//====================================
	/**
	 * �?��寻路
	 *
	 * @param p_startX		起点X坐标
	 * @param p_startY		起点Y坐标
	 * @param p_endX		终点X坐标
	 * @param p_endY		终点Y坐标
	 *
	 * @return 				找到的路�?二维数组 : [p_startX, p_startY], ... , [p_endX, p_endY])
	 */
	public List<Point> find(int p_startX, int p_startY, int p_endX, int p_endY)
	{
		long m_Start = System.currentTimeMillis();
		
		this.initLists();									// 初始化所有列表数�?
		this.m_openCount = 0;
		this.m_openId    = -1;

		this.openNote(p_startX, p_startY, 0, 0, 0);			// 起点放到�?��列表�?
		
		int currId;
		int currNoteX;
		int currNoteY;
		int preId;
		int preNoteX;
		int preNoteY;
		List<int[]> aroundNotes;
		
		int checkingId;
		
		int cost;											// (从起点移动到)节点的移动�?费G�?
		int score; 											// 节点路径评分F�?
		while (this.m_openCount > 0)
		{
			long m_End = System.currentTimeMillis();
			if (m_End - m_Start > this.m_maxTry)
			{
				this.destroyLists();
				return null;
			}
			//每次取出�?��列表�?��面的ID
			currId = this.m_openList.get(0);
			//将编码为此ID的元素列入关闭列�?
			this.closeNote(currId);
//			currNoteX = this.m_xList.get(currId);
//			currNoteY = this.m_yList.get(currId);
			Point point = this.m_List.get(currId);
			currNoteX = point.x;
			currNoteY = point.y;

			//如果终点被放入关闭列表寻路结束，返回路径
			if (currNoteX == p_endX && currNoteY == p_endY)
			{
				return this.getPath(p_startX, p_startY, currId);
			}
			//获取周围节点，排除不可�?过和已在关闭列表中的
			aroundNotes = this.getArounds(currNoteX, currNoteY);
			
			// 前面�?���?
			preId    = this.m_fatherList.get(currId);
			Point prePoint = this.m_List.get(preId);
//			preNoteX = this.m_xList.get(preId);
//			preNoteY = this.m_yList.get(preId);
			preNoteX = prePoint.x;
			preNoteY = prePoint.y;
			
			//对于周围的每�?��节点
			for(int[] note : aroundNotes)
			{
				//计算F和G�?
//					if (currNoteY == note[1])												// 横向－左�?
//					{
//						cost = this.m_movementCostList[currId] + this.COST_DIAGONAL;
//					}
//					else if (currNoteY + 2 == note[1] || currNoteY - 2 == note[1]) 			// 竖向－上下（Y坐标都差2个）
//					{
//						cost = this.m_movementCostList[currId] + this.COST_STRAIGHT;
//					}
//					else																	// 斜向－左�?左下 右上 右下
//					{
//						cost = this.m_movementCostList[currId] + this.COST_STRAIGHT;
//					}
				
				if(currNoteX - note[0] == currNoteY - note[1])
				{
					cost = this.m_movementCostList.get(currId) + COST_DIAGONAL;
				}
				else
				{
					cost = this.m_movementCostList.get(currId) + COST_STRAIGHT;
				}
				
				//拐弯		当前点不�?前一个点与将要走的点组成线段 的中点上
				if( !(currNoteX << 1 == preNoteX + note[0] && currNoteY << 1 == preNoteY + note[1]) )
				{
					cost += COST_SWERVE;	//拐弯加权
				}

				// 90度地图G值计�?
//					cost = this.m_movementCostList[currId] + ((note[0] == currNoteX || note[1] == currNoteY) ? COST_STRAIGHT : COST_DIAGONAL);
				// 90度地图估计开�?计算
				score = cost + ( Math.abs(p_endX - note[0]) + Math.abs(p_endY - note[1]) ) * COST_STRAIGHT;
//					score = cost +  Math.abs(p_endX - note[0]) * this.COST_DIAGONAL + 
//									Math.abs(p_endY - note[1]) * this.COST_VERTICAL;		// 这句会认人走得不自然		

				Point point2 = new Point(note[0], note[1]);
				if (this.isOpen(point2)) //如果节点已在播放列表�?
				{
					Node node = this.m_noteMap.get( point2 );
					checkingId = node.NOTE_ID;
					//如果新的G值比节点原来的G值小,修改F,G值，换父节点
					if (cost < this.m_movementCostList.get(checkingId))
					{
						this.m_movementCostList.set(checkingId, cost);
						this.m_pathScoreList.set(checkingId, score);
						this.m_fatherList.set(checkingId, currId);
						this.aheadNote(this.getIndex(checkingId));
					}
				}
				else //如果节点不在�?��列表�?
				{
					//将节点放入开放列�?
					this.openNote(point2, score, cost, currId);
				}
			}
		}
		//�?��列表已空，找不到路径
		this.destroyLists();
		return null;
	}

	//====================================
	//	Private Methods
	//====================================
	/**
	 * @private
	 * 将节点加入开放列�?
	 *
	 * @param p_x		节点在地图中的x坐标
	 * @param p_y		节点在地图中的y坐标
	 * @param P_score	节点的路径评�?
	 * @param p_cost	起始点到节点的移动成�?
	 * @param p_fatherId	父节�?
	 */
	private void openNote(int p_x, int p_y, int p_score, int p_cost, int p_fatherId)
	{
		this.m_openCount++;
		this.m_openId++;

		Point point = new Point(p_x, p_y);
		Node node = new Node();
		this.m_noteMap.put( point, node );
		node.NOTE_OPEN	= true;
		node.NOTE_ID    = this.m_openId;
		
//		this.m_xList.add(p_x);					//加到尾部
//		this.m_yList.add(p_y);					//加到尾部
		this.m_List.add(point);					//加到尾部
		
		this.m_pathScoreList.add(p_score);		//加到尾部
		this.m_movementCostList.add(p_cost);	//加到尾部
		this.m_fatherList.add(p_fatherId);		//加到尾部
		
		this.m_openList.add(this.m_openId);		//加到尾部
		this.aheadNote(this.m_openCount);
	}
	private void openNote(Point point, int p_score, int p_cost, int p_fatherId)
	{
		this.m_openCount++;
		this.m_openId++;
		
		Node node = new Node();
		this.m_noteMap.put( point, node );
		node.NOTE_OPEN	= true;
		node.NOTE_ID    = this.m_openId;
		
//		this.m_xList.add(p_x);					//加到尾部
//		this.m_yList.add(p_y);					//加到尾部
		this.m_List.add(point);					//加到尾部
		
		this.m_pathScoreList.add(p_score);		//加到尾部
		this.m_movementCostList.add(p_cost);	//加到尾部
		this.m_fatherList.add(p_fatherId);		//加到尾部
		
		this.m_openList.add(this.m_openId);		//加到尾部
		this.aheadNote(this.m_openCount);
	}

	/**
	 * @private
	 * 将节点加入关闭列�?
	 */
	private void closeNote(int p_id)
	{
		this.m_openCount--;
//		int noteX = this.m_xList.get(p_id);
//		int noteY = this.m_yList.get(p_id);
		Point point = this.m_List.get(p_id);
		Node node = this.m_noteMap.get( point );
		node.NOTE_OPEN   = false;
		node.NOTE_CLOSED = true;

		if (this.m_openCount <= 0)
		{
			this.m_openCount = 0;
			this.m_openList  = new ArrayList<Integer>();
			return;
		}
		this.m_openList.set(0, this.m_openList.remove(this.m_openList.size() - 1) );
		this.backNote();
	}

	/**
	 * @private
	 * �?新加入开放别表或修改了路径评分的)节点向前移动
	 */
	private void aheadNote(int p_index)
	{
		int father;
		int change;
		while (p_index > 1)
		{
			//父节点的位置
			father = (int)Math.floor(p_index >> 1);
			//如果该节点的F值小于父节点的F值则和父节点交换
			if (this.getScore(p_index) < this.getScore(father))
			{
				change 						 = this.m_openList.get(p_index - 1);
				this.m_openList.set(p_index - 1, this.m_openList.get(father - 1) );
				this.m_openList.set(father - 1, change);
				p_index = father;
			} 
			else
			{
				break;
			}
		}
	}

	/**
	 * @private
	 * �?取出�?��列表中路径评分最低的节点后从队尾移到�?���?节点向后移动
	 */
	private void backNote()
	{
		//尾部的节点被移到�?���?
		int checkIndex = 1;
		int tmp;
		int tmpX2;
		int change;

		while (true)
		{
			tmp   = checkIndex;
			tmpX2 = tmp << 1;
			// 如果有子节点
			if (tmpX2 <= this.m_openCount)
			{
				// 如果子节点的F值更�?
				if(this.getScore(checkIndex) > this.getScore(tmpX2))
				{
					// 记节点的新位置为子节点位�?
					checkIndex = tmpX2;
				}
				//如果有两个子节点
				if (tmpX2 + 1 <= this.m_openCount)
				{
					// 如果第二个子节点F值更�?
					if (this.getScore(checkIndex) > this.getScore(tmpX2 + 1))
					{
						// 更新节点新位置为第二个子节点位置
						checkIndex = tmpX2 + 1;
					}
				}
			}
			
			if (tmp == checkIndex)	// 如果节点位置没有更新结束排序
			{
				break;
			}
			else 					// 反之和新位置交换，继续和新位置的子节点比较F�?
			{
				change = this.m_openList.get(tmp - 1);
				this.m_openList.set(tmp - 1, this.m_openList.get(checkIndex - 1) );
				this.m_openList.set(checkIndex - 1, change);
			}
		}
	}
	
	/**
	 * @private
	 * 判断某节点是否在�?��列表
	 */		
	private boolean isOpen(int p_x, int p_y)
	{
		Point point = new Point(p_x, p_y);
		if (this.m_noteMap.get( point ) == null){
			return false;
		}
		return this.m_noteMap.get( point ).NOTE_OPEN;
	}
	
	private boolean isOpen(Point point)
	{
		if (this.m_noteMap.get( point ) == null){
			return false;
		}
		return this.m_noteMap.get( point ).NOTE_OPEN;
	}

	/**
	 * @private
	 * 判断某节点是否在关闭列表�?
	 */
	private boolean isClosed(int p_x, int p_y)
	{
		Point point = new Point(p_x, p_y);
		if (this.m_noteMap.get( point ) == null){
			return false;
		}
		return this.m_noteMap.get( point ).NOTE_CLOSED;
	}

	/**
	 * @private
	 * 获取某节点的周围节点，排除不能�?过和已在关闭列表中的
	 */
	private List<int[]> getArounds(int p_x, int p_y)
	{
//		Object[] arr = new Object[]{};
		List<int[]> list = new ArrayList<int[]>();
		int checkX;
		int checkY;
		
		//y&1 y是偶数是0，不属于红色方块某个，x左边-1.奇数�? 意�?是，这个节点属于红色方块，说明x坐标不变
		
		//右下
//			checkX = p_x + (p_y & 1);
//			checkY = p_y + 1;
		checkX = p_x + 1;
		checkY = p_y;
		boolean canRightBottom = isBalk || isPass(p_x, p_y, checkX, checkY);
		if (canRightBottom && !this.isClosed(checkX, checkY))
		{
//			arr.push([checkX, checkY]);	//加到尾部
			list.add(new int[]{checkX, checkY});
		}
		//左下
//			checkX = p_x - 1 + (p_y & 1);
//			checkY = p_y + 1;
		checkX = p_x;
		checkY = p_y - 1;
		boolean canLeftBottom = isBalk || isPass(p_x, p_y, checkX, checkY);
		if (canLeftBottom && !this.isClosed(checkX, checkY))
		{
//			arr.push([checkX, checkY]);//加到尾部
			list.add(new int[]{checkX, checkY});
		}
		//左上
//			checkX = p_x - 1 + (p_y & 1); 			
//			checkY = p_y - 1;
		checkX = p_x - 1;
		checkY = p_y;
		boolean canLeftTop = isBalk || isPass(p_x, p_y, checkX, checkY);
		if (canLeftTop && !this.isClosed(checkX, checkY))
		{
//			arr.push([checkX, checkY]);	//加到尾部
			list.add(new int[]{checkX, checkY});
		}
		//右上
//			checkX = p_x + (p_y & 1);
//			checkY = p_y - 1;
		checkX = p_x;
		checkY = p_y + 1;
		boolean canRightTop = isBalk || isPass(p_x, p_y, checkX, checkY);
		if (canRightTop && !this.isClosed(checkX, checkY))
		{
//			arr.push([checkX, checkY]);//加到尾部
			list.add(new int[]{checkX, checkY});
		}
		//�?
//			checkX = p_x + 1;
//			checkY = p_y;
		checkX = p_x + 1;
		checkY = p_y + 1;
		boolean canRight = isBalk || isPass(p_x, p_y, checkX, checkY);
		if (canRight && canRightTop && canRightBottom && !this.isClosed(checkX, checkY))
		{
//			arr.push([checkX, checkY]);//加到尾部
			list.add(new int[]{checkX, checkY});
		}
		//�?
//			checkX = p_x;
//			checkY = p_y + 2;
		checkX = p_x + 1;
		checkY = p_y - 1;
		boolean canDown = isBalk || isPass(p_x, p_y, checkX, checkY);
		if (canDown && canLeftBottom && canRightBottom && !this.isClosed(checkX, checkY))
		{
//			arr.push([checkX, checkY]);//加到尾部
			list.add(new int[]{checkX, checkY});
		}
		//�?
//			checkX = p_x - 1;
//			checkY = p_y;
		checkX = p_x - 1;
		checkY = p_y - 1;
		boolean canLeft = isBalk || isPass(p_x, p_y, checkX, checkY);
		if (canLeft && canLeftTop && canLeftBottom && !this.isClosed(checkX, checkY))
		{
//			arr.push([checkX, checkY]);//加到尾部
			list.add(new int[]{checkX, checkY});
		}
		//�?
//			checkX = p_x;
//			checkY = p_y - 2;
		checkX = p_x - 1;
		checkY = p_y + 1;
		boolean canUp = isBalk || isPass(p_x, p_y, checkX, checkY);
		if (canUp && canLeftTop && canRightTop && !this.isClosed(checkX, checkY))
		{
//			arr.push([checkX, checkY]);//加到尾部
			list.add(new int[]{checkX, checkY});
		}

		return list;
	}

	/**
	 * @private
	 * 获取路径
	 *
	 * @param p_startX	起始点X坐标
	 * @param p_startY	起始点Y坐标
	 * @param p_id		终点的ID
	 *
	 * @return 			路径坐标(Point)数组
	 */
	private List<Point> getPath(int p_startX, int p_startY, int p_id)
	{
//		Object[] arr = new Object[]{};
		List<Point> list = new ArrayList<Point>();
//		int noteX = this.m_xList.get(p_id);
//		int noteY = this.m_yList.get(p_id);
		Point point = this.m_List.get(p_id);
		while (point.x != p_startX || point.y != p_startY)
		{
////		arr.unshift([noteX, noteY]);		//加到头部
			list.add(0, point);
			
			p_id  = this.m_fatherList.get(p_id);
//			noteX = this.m_xList.get(p_id);
//			noteY = this.m_yList.get(p_id);
			point = this.m_List.get(p_id);
			
		}
//		arr.unshift([p_startX, p_startY]);		//加到头部
		list.add(0, new Point(p_startX, p_startY));
		this.destroyLists();
		return list;
	}
	
	/**
	 * @private
	 * 获取某ID节点在开放列表中的索�?�?�?��)
	 */		
	private int getIndex(int p_id)
	{
		int i = 1;
		for(int id : this.m_openList)
		{
			if (id == p_id)
			{
				return i;
			}
			i++;
		}
		return -1;
	}
	/**
	 * @private
	 * 获取某节点的路径评分
	 * 
	 * @param p_index	节点在开启列表中的索�?�?�?��)
	 */		
	private int getScore(int p_index)
	{
		return this.m_pathScoreList.get( this.m_openList.get(p_index - 1) );
	}
	/**
	 * @private
	 * 初始化数�?
	 */		
	private void initLists()
	{
		this.m_openList 		= new ArrayList<Integer>(20);
//		this.m_xList 			= new ArrayList<Integer>();
//		this.m_yList 			= new ArrayList<Integer>();
		this.m_List 			= new ArrayList<Point>(20);
		this.m_pathScoreList 	= new ArrayList<Integer>(20);
		this.m_movementCostList = new ArrayList<Integer>(20);
		this.m_fatherList 		= new ArrayList<Integer>(20);
		this.m_noteMap 			= new HashMap<Point, Node>(20);
	}
	
	/**
	 * @private
	 * �?��数组
	 */		
	private void destroyLists()
	{
//		this.m_openList.clear();
		this.m_openList 		= null;
		
//		this.m_xList.clear();
//		this.m_xList  			= null;
//		
//		this.m_yList.clear();
//		this.m_yList 			= null;
//		this.m_List.clear();
		this.m_List				= null;
		
//		this.m_pathScoreList.clear();
		this.m_pathScoreList    = null;
		
//		this.m_movementCostList.clear();
		this.m_movementCostList = null;
		
//		this.m_fatherList.clear();
		this.m_fatherList 		= null;
		
//		this.m_noteMap.clear();
		this.m_noteMap 			= null;
	}
	
	/**
	 * 是否为障�?
	 * @param startX	始点X坐标
	 * @param startY	始点Y坐标
	 * @param endX		终点X坐标
	 * @param endY		终点Y坐标
	 * @return -1为未�?0为�?�? 1为障�? 2 为半透明, 3 为摆摊位
	 */
	public int isBlock(int startX, int startY, int endX, int endY)
	{
		int mapWidth	= data.length;
		int mapHeight	= data[0].length;
		
		if (endX < 0 || endX >= mapWidth || endY < 0 || endY >= mapHeight)
		{
			return -1;
		}
		return data[endX][endY];
	}
	
	/**
	 * 是否可能通过
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return
	 */
	public boolean isPass(int startX, int startY, int endX, int endY){
		int mask = isBlock(startX, startY, endX, endY);
		return mask != MaskTypes.PATH_BARRIER && mask != -1;
	}
	
	class Node{
		boolean NOTE_OPEN = false;						//(单个)节点数组 是否在开启列表中 索引
		boolean NOTE_CLOSED = false;					//(单个)节点数组 是否在关闭列表中 索引
		int NOTE_ID;
	}
}
