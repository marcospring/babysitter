package com.zhangk.babysitter.utils.data;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class Test {

	public static void main(String[] args) {
		VelocityEngine v = new VelocityEngine();
		v.setProperty(VelocityEngine.INPUT_ENCODING, "UTF-8");
		v.setProperty(VelocityEngine.OUTPUT_ENCODING, "UTF-8");
		v.init();
		Template t = v.getTemplate("D:\\t.vm");
		VelocityContext c = new VelocityContext();
		c.put("aaa", "aaa");
		StringWriter writer = new StringWriter();
		t.merge(c, writer);
		System.out.println(writer);
	}

}
