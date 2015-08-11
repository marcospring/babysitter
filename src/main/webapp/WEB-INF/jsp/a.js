Ext.onReady(function(){
	if(Ext.BLANK_IMAGE_URL.substring(0,4)!="data"){
		Ext.BLANK_IMAGE_URL="./images/s.gif";
	}
	new Ext.Viewport({
		layout:'fit',
		items:[{
			xtype:"panel",
			title:"welcome",
			html:""
		}]
	});
})