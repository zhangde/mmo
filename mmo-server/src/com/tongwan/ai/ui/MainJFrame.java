package com.tongwan.ai.ui;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

/**
 * @author zhangde
 * @date 2013年12月27日
 */
public class MainJFrame implements MouseListener{
	
	private JFrame f;
	private TreePanel treePanel;
	private AiListPanel listPanel;
	public static int width=700;
	public static int height=700;
	public void init(){
		
		f = new JFrame("JTreeDemo");
        f.setSize(width, height);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        treePanel=new TreePanel(this);
        listPanel=new AiListPanel(this);
        f.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(listPanel);
        f.add(treePanel);
        double _width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double _height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        f.setLocation( (int) (_width - f.getWidth()) / 2,
                    (int) (_height - f.getHeight()) / 2);
        f.setVisible(true);
        f.addMouseListener(this);
        f.setJMenuBar(new MainMenuBar(this));
        f.addComponentListener(new ComponentAdapter(){
        	public void componentResized(ComponentEvent e) {
                width=f.getWidth();
                treePanel.changeSize();
            }
        });
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		treePanel.getTree().clearSelection();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	/**
	 * @return the f
	 */
	public JFrame getF() {
		return f;
	}
	/**
	 * @return the treePanel
	 */
	public TreePanel getTreePanel() {
		return treePanel;
	}
	/**
	 * @return the listPanel
	 */
	public AiListPanel getListPanel() {
		return listPanel;
	}
	
}
