package com.tongwan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

public class MainJPanel extends JPanel {
	private int column=5;
	private int rows=5;
	private byte[][] data;
	private Map<Long,Map> monsteres;
	public MainJPanel(){
		this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		setBackground(Color.white);
	}
	public void setMapData(byte[][] data){
		this.data=data;
		this.rows=data.length;
		this.column=data[0].length;
	}
	public void setMonster(Map<Long,Map> monsteres){
		this.monsteres=monsteres;
		this.repaint();
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int width=getWidth();
		int height=getHeight();
		int columnSpace=width/column;
		int rowSpace=height/rows;
		for(int i=0;i<column;i++){
			g2.drawLine(i*columnSpace, 0, i*columnSpace, height);
		}
		for(int i=0;i<rows;i++){
			g2.drawLine(0, i*rowSpace, width, i*rowSpace);
		}
		if(data!=null){
			int i=0;
			
			for(byte[] row:data){
				int j=0;
				for(byte column:row){
					if(column==1){
						g2.fillRect(j*columnSpace, i*rowSpace, columnSpace, rowSpace);
					}
					j++;
				}
				i++;
			}
		}
		
		if(monsteres!=null){
			g2.setColor(Color.red);
			Set<Entry<Long, Map>> set=monsteres.entrySet();
			for(Entry<Long, Map> o :set){
				Map m=o.getValue();
				int x=(int) m.get("x");
				int y=(int) m.get("y");
				g2.fillRect(x*columnSpace, y*rowSpace, columnSpace, rowSpace);
			}
		}
	}
	
}
