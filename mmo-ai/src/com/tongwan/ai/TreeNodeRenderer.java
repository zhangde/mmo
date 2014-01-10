package com.tongwan.ai;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author zhangde
 * @date 2013-12-29
 */
public class TreeNodeRenderer extends DefaultTreeCellRenderer{
	private static Icon selectIcon ;
	public TreeNodeRenderer(){
		try {
			Image image=ImageIO.read(new File("select.png"));
			selectIcon=new ImageIcon(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		AITreeNode node=(AITreeNode)value;
		switch (node.getType()) {
		case SELECTOR:
			setBackgroundNonSelectionColor(Color.YELLOW);
			break;
		case SEQUENCE:
			setBackgroundNonSelectionColor(Color.CYAN);
			break;
		case CONDITION:
			setBackgroundNonSelectionColor(Color.RED);
			break;
		case ACTION:
			setBackgroundNonSelectionColor(Color.GREEN);
			break;
		default:
			break;
		}
		
		return this;
	}
	
}
