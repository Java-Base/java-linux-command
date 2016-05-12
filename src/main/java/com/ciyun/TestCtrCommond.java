package com.ciyun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * java远程执行linux命令
 * http://hbiao68.iteye.com/blog/1624485
 * 
 * @author 	Lian
 * @date	2016年5月5日
 * @since	1.0	
 */
public class TestCtrCommond {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

//		String hostname = "114.55.100.72";
		String hostname = "112.74.74.201";
		String username = "root";
//		String password = "Guyanliang1989";
		String password = "Ciyun@520";
		// 指明连接主机的IP地址
		Connection conn = new Connection(hostname);
		Session ssh = null;
		try {
			// 连接到主机
			conn.connect();
			// 使用用户名和密码校验
			boolean isconn = conn.authenticateWithPassword(username, password);
			if (!isconn) {
				System.out.println("用户名称或者是密码不正确");
			} else {
				System.out.println("已经连接OK");
				ssh = conn.openSession();
				// 使用多个命令用分号隔开
				// ssh.execCommand("pwd;cd /tmp;mkdir hb;ls;ps -ef|grep
				// weblogic");
//				ssh.execCommand("cd /app/weblogic/Oracle/Middleware/user_projects/domains/base_domain;./startWebLogic.sh &");
//				ssh.execCommand("top -n 1");
				ssh.execCommand("pwd");
				// 只允许使用一行命令，即ssh对象只能使用一次execCommand这个方法，多次使用则会出现异常
				// ssh.execCommand("mkdir hb");
				// 将屏幕上的文字全部打印出来
				InputStream is = new StreamGobbler(ssh.getStdout());
				BufferedReader brs = new BufferedReader(new InputStreamReader(is));
				while (true) {
					String line = brs.readLine();
					if (line == null) {
						break;
					}
					System.out.println(line);
				}

			}
			// 连接的Session和Connection对象都需要关闭
			ssh.close();
			conn.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
