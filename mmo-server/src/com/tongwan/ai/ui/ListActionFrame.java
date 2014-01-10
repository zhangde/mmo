package com.tongwan.ai.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.tongwan.ai.node.ConditionNode;
import com.tongwan.ai.type.Action;
import com.tongwan.ai.type.Condition;

/**
 * 条件列表窗口
 * @author zhangde
 * @date 2013年12月30日
 */
public class ListActionFrame extends JFrame{
	public static int width=600;
	public static int height=600;
	private final MainJFrame mainJFrame;
	/** 是否新建条件*/
	private boolean isNewCond;
	public ListActionFrame(final MainJFrame mainJFrame){
		super("动作列表");
		this.mainJFrame=mainJFrame;
		setSize(width, height);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel leftPanel=new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, height-50));
		leftPanel.setBackground(Color.white);
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		add(leftPanel);
		final JPanel rightPanel=new JPanel();
		rightPanel.setPreferredSize(new Dimension(width- leftPanel.getWidth()-150, height-50));
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.red, 1));
		
		
		add(rightPanel);
		final DefaultListModel listModel = new DefaultListModel();
		refreshLeftPanel(listModel);
	    JList list = new JList(listModel);
	    
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    //更新条件内容
	    list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent event) {
//				System.out.println(event.getFirstIndex());
				JList source=(JList) event.getSource();
				final Action o=(Action) source.getSelectedValue();
				if(o==null){
					return;
				}
				isNewCond=false;
				rightPanel.removeAll();
				
				JLabel nameLabel=new JLabel("动作名称:");
				final JTextField nameText=new JTextField();
				nameText.setColumns(30);
				nameText.setText(o.getName());
				JLabel keyLabel=new JLabel("动作KEY:");
				final JTextField keyText=new JTextField();
				keyText.setColumns(30);
				keyText.setText(o.getKey());
				JLabel summaryLabel=new JLabel("动作描述:");
				final JTextArea textArea = new JTextArea();
				textArea.setColumns(30);
				textArea.setRows(20);
				textArea.setText(o.getSummary());
				textArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				JButton save=new JButton("更新动作");
				save.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						DataContext.updateAction(o.getId(),nameText.getText(), keyText.getText(), textArea.getText());
						refreshLeftPanel(listModel);
						mainJFrame.getTreePanel().getTree().updateUI();
						rightPanel.removeAll();
						rightPanel.validate();
						rightPanel.repaint();
					}
				});
				rightPanel.add(nameLabel);
				rightPanel.add(nameText);
				rightPanel.add(keyLabel);
				rightPanel.add(keyText);
				rightPanel.add(summaryLabel);
				rightPanel.add(textArea);
				rightPanel.add(save);
				rightPanel.validate();
				rightPanel.repaint();
			}
		});
	    
	    
	    JButton newCondButton=new JButton("添加动作");
	    newCondButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				isNewCond=true;
				rightPanel.removeAll();
				
				JLabel nameLabel=new JLabel("动作名称:");
				final JTextField nameText=new JTextField();
				nameText.setColumns(30);
				JLabel keyLabel=new JLabel("动作KEY:");
				final JTextField keyText=new JTextField();
				keyText.setColumns(30);
				JLabel summaryLabel=new JLabel("动作描述:");
				final JTextArea textArea = new JTextArea();
				textArea.setColumns(30);
				textArea.setRows(20);
				textArea.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				JButton save=new JButton("保存动作");
				save.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						if(isNewCond){
							
							DataContext.addAction(nameText.getText(), keyText.getText(), textArea.getText());
							refreshLeftPanel(listModel);
							rightPanel.removeAll();
							rightPanel.validate();
							rightPanel.repaint();
							System.out.println(DataContext.toJson());
						}
						isNewCond=false;
					}
				});
				rightPanel.add(nameLabel);
				rightPanel.add(nameText);
				rightPanel.add(keyLabel);
				rightPanel.add(keyText);
				rightPanel.add(summaryLabel);
				rightPanel.add(textArea);
				rightPanel.add(save);
				rightPanel.validate();
				rightPanel.repaint();
			}
		});
	    leftPanel.add(newCondButton);
	    leftPanel.add(list);
	}
	private void refreshLeftPanel(DefaultListModel listModel){
		listModel.removeAllElements();
		for(Action c:DataContext.getActions()){
			listModel.addElement(c);
		}
	}
}
