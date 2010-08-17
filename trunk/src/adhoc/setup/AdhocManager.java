package adhoc.setup;

import android.app.Activity;
import android.net.wifi.WifiManager;

public class AdhocManager {
	Activity parent;
	private WifiManager wifiManager;
	private String ip = "192.168.2.";
	
	public AdhocManager(Activity parent, WifiManager wifiManager){
		this.parent = parent;
		this.wifiManager = wifiManager;
	}

//	public boolean startAdhocNetwork(int phoneType, int nodeAddress){
//		disableWifi();
//		ip = ip+Integer.toString(nodeAddress);
//		int result = NativeCommand.runCommand("su -c \""+" startstopadhoc start "+phoneType+" "+ip+"\"");
//		
//		return (result == 0 ? true:false);
//	}
//	
//	public boolean stopAdhocNetwork(int phoneType){
//		int result = NativeCommand.runCommand("su -c \""+" startstopadhoc start "+phoneType+" "+ip+"\"");
//		return (result == 0 ? true:false);
//	}
	
	
	
	 private void disableWifi() {
	    	if (wifiManager.isWifiEnabled()){
	    		wifiManager.setWifiEnabled(false);	    		
	    	}
	    }
}

