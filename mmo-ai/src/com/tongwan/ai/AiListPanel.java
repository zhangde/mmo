package com.tongwan.ai;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.tongwan.common.ai.behaviortree.BehaviorTree;

/**
 * 行为列表面板
 * @author zhangde
 * @date 2013年12月24日
 */
public class AiListPanel extends JPanel{
	public static int width=100;
	private MainJFrame mainJFrame;
	private DefaultListModel listModel;
	private JList list;
	public BehaviorTree current;
	public AiListPanel(final MainJFrame mainJFrame){
		this.mainJFrame=mainJFrame;
		this.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		setBackground(Color.white);
		setPreferredSize(new Dimension(width, 500));
		listModel = new DefaultListModel();
	    list = new JList(listModel);
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				JList source=(JList) event.getSource();
				final BehaviorTree o=(BehaviorTree) source.getSelectedValue();
				mainJFrame.getTreePanel().showTree(o);
				current=o;
			}
		});
	    add(list);
	}
	/**
	 * 更新UI及数据
	 * @param o
	 */
	public void createNewElement(BehaviorTree o){
		if(DataContext.addBehaviorTree(o)){
			listModel.addElement(o);
			int size=listModel.getSize();
		    list.setSelectedIndex(size-1);
		}
	}
	/** 
	 * 只更新UI
	 * @param o
	 */
	public void addListElement(BehaviorTree o){
		listModel.addElement(o);
		int size=listModel.getSize();
		list.setSelectedIndex(size-1);
	}
	public void refreshThis(){
		listModel.removeAllElements();
		for(BehaviorTree o:DataContext.getTrees()){
			addListElement(o);
		}
	}
}
