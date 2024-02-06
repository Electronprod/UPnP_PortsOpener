package electron;

import java.awt.Frame;

import javax.swing.JOptionPane;

import com.dosse.upnp.UPnP;

public class Methods {
	public static boolean isNum(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        int d = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	public static void openTCP(int PORT) {
		if (UPnP.isUPnPAvailable()) { // is UPnP available?
			if (UPnP.isMappedTCP(PORT)) { // is the port already mapped?
				System.out.println("UPnP port forwarding not enabled: port is already mapped");
				JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding not enabled: port is already mapped", "UPnP", JOptionPane.ERROR_MESSAGE);
			} else if (UPnP.openPortTCP(PORT)) { // try to map port
				System.out.println("UPnP port forwarding enabled");
				JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding enabled", "UPnP", JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("UPnP port forwarding failed");
				JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding failed", "UPnP", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			System.out.println("UPnP is not available");
			JOptionPane.showMessageDialog(new Frame(), "UPnP is not available. Check router's settings.", "UPnP", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void openUDP(int PORT) {
		if (UPnP.isUPnPAvailable()) { // is UPnP available?
			if (UPnP.isMappedUDP(PORT)) { // is the port already mapped?
				System.out.println("UPnP port forwarding not enabled: port is already mapped");
				JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding not enabled: port is already mapped", "UPnP", JOptionPane.ERROR_MESSAGE);
			} else if (UPnP.openPortUDP(PORT)) { // try to map port
				System.out.println("UPnP port forwarding enabled");
				JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding enabled", "UPnP", JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("UPnP port forwarding failed");
				JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding failed", "UPnP", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			System.out.println("UPnP is not available");
			JOptionPane.showMessageDialog(new Frame(), "UPnP is not available. Check router's settings.", "UPnP", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void closeUDP(int PORT) {
		UPnP.closePortUDP(PORT);
		JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding disabled", "UPnP", JOptionPane.INFORMATION_MESSAGE);
	}
	public static void closeTCP(int PORT) {
		UPnP.closePortTCP(PORT);
		JOptionPane.showMessageDialog(new Frame(), "UPnP port forwarding disabled", "UPnP", JOptionPane.INFORMATION_MESSAGE);
	}
}
