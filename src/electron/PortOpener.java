package electron;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.swing.event.*;

import com.dosse.upnp.UPnP;

public class PortOpener extends JPanel {
	private JLabel externalip;
	private JButton opentcp;
	private JButton openudp;
	private JLabel localip;
	private JTextField portselector;
	private JLabel jcomp6;

	public static void main(String args[]) {
		JFrame frame = new JFrame("UPnP port opener");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new PortOpener());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

	public PortOpener() {
		if (!UPnP.isUPnPAvailable()) {
			System.out.println("UPnP is not available");
			JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding failed. Check router's settings.", "UPnP", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		// construct components
		externalip = new JLabel("External IP: "+UPnP.getExternalIP());
		opentcp = new JButton("TCP:"+getState(UPnP.isMappedTCP(4137)));
		openudp = new JButton("UDP:"+getState(UPnP.isMappedUDP(4137)));
		localip = new JLabel("Local IP: "+UPnP.getLocalIP());
		portselector = new JTextField(5);
		portselector.setText("4137");
		jcomp6 = new JLabel("Enter port:");

		// adjust size and set layout
		setPreferredSize(new Dimension(222, 118));
		setLayout(null);

		// add components
		add(externalip);
		add(opentcp);
		add(openudp);
		add(localip);
		add(portselector);
		add(jcomp6);

		// set component bounds (only needed by Absolute Positioning)
		externalip.setBounds(5, 5, 225, 20);
		opentcp.setBounds(115, 85, 100, 25);
		openudp.setBounds(10, 85, 100, 25);
		localip.setBounds(5, 25, 225, 20);
		portselector.setBounds(75, 55, 65, 15);
		jcomp6.setBounds(5, 50, 70, 25);
		opentcp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = getPort();
				if(port==0) {return;}
				System.out.println("Port: "+port);
				if(UPnP.isMappedTCP(port)) {
					Methods.closeTCP(port);
				}else {
					Methods.openTCP(port);
				}
				opentcp.setText("TCP:"+getState(UPnP.isMappedTCP(port)));
			}
		});
		openudp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = getPort();
				if(port==0) {return;}
				System.out.println("Port: "+port);
				if(UPnP.isMappedUDP(port)) {
					Methods.closeUDP(port);
				}else {
					Methods.openUDP(port);
				}
				openudp.setText("UDP:"+getState(UPnP.isMappedUDP(port)));
			}
		});
	}
	private int getPort() {
		String text = portselector.getText();
		if(Methods.isNum(text)) {
			return Integer.parseInt(text);
		}else {
			JOptionPane.showMessageDialog(new Frame(), "Incorrect port.", "Format error", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
	}
	private String getState(boolean state) {
		if(state) {
			return "close";
		}else {
			return "open";
		}
	}
}