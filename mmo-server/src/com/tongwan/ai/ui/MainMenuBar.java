package com.tongwan.ai.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.tongwan.ai.BehaviorTree;
import com.tongwan.common.io.FileX;

public class MainMenuBar extends JMenuBar{
	private final MainJFrame mainJFrame;
	public MainMenuBar(final MainJFrame mainJFrame){
		this.mainJFrame=mainJFrame;
		JMenu fileMenu = new JMenu("文件");  
	    fileMenu.setMnemonic(KeyEvent.VK_F);  
	    this.add(fileMenu); 
	    JMenuItem newMenuItem = new JMenuItem("新建", KeyEvent.VK_N);  
	    fileMenu.add(newMenuItem);  
	    JMenuItem openMenuItem = new JMenuItem("打开...", KeyEvent.VK_O);  
	    fileMenu.add(openMenuItem);  
	    openMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(mainJFrame.getF());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: "+chooser.getSelectedFile().getAbsolutePath());
					DataContext.load(chooser.getSelectedFile().getAbsolutePath());
					List<BehaviorTree> trees=DataContext.getTrees();
					if(trees!=null && !trees.isEmpty()){
						for(BehaviorTree tree:trees){
							mainJFrame.getListPanel().addListElement(tree);
						}
					}
//				    mainJFrame.getListPanel().refreshThis();   
				}
			}
		});
	    JMenuItem saveMenuItem = new JMenuItem("保存");  
	    fileMenu.add(saveMenuItem);
	    saveMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(DataContext.getPath()!=null){
					FileX.newFile(DataContext.getPath(), DataContext.toJson());
				}else{
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogType(JFileChooser.SAVE_DIALOG);
				    int returnVal = chooser.showSaveDialog(mainJFrame.getF());
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				       System.out.println("You chose to open this file: "+chooser.getSelectedFile().getAbsolutePath());
				       FileX.newFile(chooser.getSelectedFile().getAbsolutePath()+".ai", DataContext.toJson());
				       
				    }
				}
				
			}
		});
	    JMenu aiMenu = new JMenu("AI");  
	    JMenuItem newTree = new JMenuItem("新建行为树");  
	    aiMenu.add(newTree);
	    newTree.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name=JOptionPane.showInputDialog(null,"新行为树名称","新建",JOptionPane.INFORMATION_MESSAGE);
//				TreePanel treePanel=mainJFrame.getTreePanel();
//				treePanel.newTree(name,mainJFrame);
				BehaviorTree bt=new BehaviorTree(name);
				AiListPanel aiListPanel=mainJFrame.getListPanel();
				aiListPanel.createNewElement(bt);
				
				
			}
		});
	    JMenuItem newCondition = new JMenuItem("查看判定");  
	    aiMenu.add(newCondition);  
	    JMenuItem newAction = new JMenuItem("查看动作");  
	    aiMenu.add(newAction); 
//	    fileMenu.setMnemonic(KeyEvent.VK_F);  
	    this.add(aiMenu);
	    newCondition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ListCondFrame condListPanel=new ListCondFrame(mainJFrame);
				condListPanel.setVisible(true);
			}
		});
	    newAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ListActionFrame actionList=new ListActionFrame(mainJFrame);
				actionList.setVisible(true);
			}
		});
	}
}
