/**
 * 
 */
 Ext.Loader.setConfig({
 	enabled:true,
 	paths:{
 		'babysitter':'app'
 	}
 });
 Ext.ns('babysitter.app');
 Ext.require('babysitter.LoginWin');
 
 Ext.onReady(function(){
 	Ext.state.Manager.setProvider(new Ext.state.CookieProvider({
 		expires: new Date(new Date.getTime()+1000*60*60)
 	}));
 	if(Ext.util.Cookie.get("hasLogin")=="true"){
 		Ext.create("babysitter.Application");
 	}else{
 		babysitter.LoginWin.show();
 	}
 	
 });
 
