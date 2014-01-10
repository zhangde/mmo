package com.tongwan.ai;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.tongwan.common.ai.behaviortree.node.ActionNode;
import com.tongwan.common.ai.behaviortree.type.Action;

/**
 * 动作列表选择窗口
 * @author zhangde
 * @date 2013年12月30日
 */
public class SelectActionListFrame extends JFrame{
	public static int width=200;
	public static int height=600;
	private final MainJFrame mainJFrame;
	public SelectActionListFrame(final MainJFrame mainJFrame){
		super("动作列表");
		this.mainJFrame=mainJFrame;
		setSize(width, height);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel leftPanel=new JPanel();
		leftPanel.setPreferredSize(new Dimension(width-10, height-50));
		leftPanel.setBackground(Color.white);
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		add(leftPanel);
		final DefaultListModel listModel = new DefaultListModel();
		refreshLeftPanel(listModel);
	    JList list = new JList(listModel);
	    
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    //更新条件内容
	    list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent event) {
				JList source=(JList) event.getSource();
				if(source.getValueIsAdjusting()){
					final Action o=(Action) source.getSelectedValue();
//					AITreeNode newNode=new AITreeNode(NodeType.CONDITION,o);
					ActionNode newNode=new ActionNode(mainJFrame.getListPanel().current,o.getId()) ;
					mainJFrame.getTreePanel().addNode(newNode);
				}
			}
		});
	    leftPanel.add(list);
	}
	private void refreshLeftPanel(DefaultListModel listModel){
		listModel.removeAllElements();
		for(Action c:DataContext.getActions()){
			listModel.addElement(c);
		}
	}
}
