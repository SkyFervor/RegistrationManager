package com.skyfervor.framework.utility;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件发送类，文件内容可以为html格式的字符串
 *
 */
public class MailUtils {

	private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

	private final static String Host = "mail.skyfervor.com"; //	邮件服务器地址
	private final static int Port = 25; //	邮件服务器端口
	private final static String User = "CRMfeedback@skyfervor.com"; //  邮件默认发件人账户
	private final static String Password = "n61GsqST"; //	邮件默认发件人密码

	/**
	 * 发送邮件
	 *
	 * @param to
	 *            收件人邮箱
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容，内容格式可以是普通字符串 或者 html格式 内容
	 * @return 是否发送成功
	 */
	public static boolean Send(String to, String subject, String content) {
		return Send(to, null, subject, content, null);
	}

	/**
	 * 发送邮件，带抄送功能，使用示例 MailUtil.Send("kunliu_1@skyfervor.com",
	 * "minglinc@skyfervor.com;kunliu_1@skyfervor.com" ,"123", "收到了吗？",null);
	 *
	 * @param to
	 *            收件人邮箱
	 * @param cc
	 *            抄送邮箱，多人以;号分开
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容，内容格式可以是普通字符串 或者 html格式 内容
	 * @param fileName
	 *            附件地址
	 * @return 是否发送成功
	 */
	public static boolean Send(String to, String cc, String subject, String content, String fileName) {
		JavaMailSenderImpl sender = getSender(); // 配置文件对象
		Properties props = new Properties(); // 生成属性对象
		props.put("mail.smtp.auth", "true"); // 是否进行验证
		Session session = Session.getInstance(props); // 创建会话
		sender.setSession(session); // 为发送器指定会话
		MimeMessage mail = sender.createMimeMessage(); // 制定媒体格式邮件
		setHelper(mail, to, cc, subject, content, fileName); // 为媒体邮件设置相关信息
		try {
			sender.send(mail); // 发送
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static JavaMailSenderImpl getSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(Host);
		sender.setPort(Port);
		sender.setUsername(User);
		sender.setPassword(Password);
		sender.setDefaultEncoding("UTF-8");
		return sender;
	}

	private static void setHelper(MimeMessage mail, String to, String cc, String subject, String content,
			String fileName) {

		MimeMessageHelper helper = new MimeMessageHelper(mail);
		try {
			helper.setTo(to); // 收件人
			helper.setCc(StringUtils.isEmpty(cc) ? new String[] {} : cc.split(";")); // 抄送邮件地址数组
			helper.setSubject(subject); // 标题
			helper.setFrom(User); // 来自
			helper.setText(content, true); // 邮件内容，第二个参数指定发送的是HTML格式

			if (StringUtils.isEmpty(fileName))
				return;

			FileSystemResource image = new FileSystemResource(fileName);
			helper.addAttachment("attachment", image); //可以设置 附件，比如图片
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param from
	 *            发件人
	 * @param password
	 *            发件人密码
	 * @param to
	 *            收件人
	 * @param cc
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param context
	 *            内容
	 * @param fileName
	 *            附件
	 * @return true 发送成功 false 发送失败
	 */
	public static boolean sendMail(String from, String password, String[] to, String[] cc, String subject,
			String context, String fileName) {

		JavaMailSenderImpl sender = getSender(from, password); // 配置文件对象
		Properties props = new Properties(); // 生成属性对象
		props.put("mail.smtp.auth", "true"); // 是否进行验证
		Session session = Session.getInstance(props); // 创建会话
		sender.setSession(session); // 为发送器指定会话
		MimeMessage mail = sender.createMimeMessage(); // 制定媒体格式邮件
		setMail(mail, from, to, cc, subject, context, fileName); // 为媒体邮件设置相关信息

		try {
			sender.send(mail);
		} catch (MailException me) {
			logger.error("sendMail -> Exception:{}", me);
			return false;
		}
		return true;
	}

	/**
	 * @param user
	 *            用户名
	 * @param password
	 *            密码
	 * @return 邮件发送容器
	 */
	private static JavaMailSenderImpl getSender(String user, String password) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(Host);
		sender.setPort(Port);
		sender.setUsername(user);
		sender.setPassword(password);
		sender.setDefaultEncoding("UTF-8");
		return sender;
	}

	/**
	 * @param mail
	 *            邮件
	 * @param from
	 *            发件人
	 * @param to
	 *            收件人
	 * @param cc
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param context
	 *            内容
	 * @param fileName
	 *            附件
	 * @return true 设置邮件信息成功 false 设置邮件信息失败
	 */
	private static boolean setMail(MimeMessage mail, String from, String[] to, String[] cc, String subject,
			String context, String fileName) {
		MimeMessageHelper messageHelper = new MimeMessageHelper(mail);
		try {
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setCc(cc == null ? new String[] {} : cc);
			messageHelper.setSubject(subject);
			messageHelper.setText(context, true);

			if (StringUtils.isNotEmpty(fileName)) {
				FileSystemResource file = new FileSystemResource(fileName);
				messageHelper.addAttachment("attachment", file);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error("setMail -> Exception:{}", e);
			return false;
		}
		return true;
	}

	/**
	 * @param from
	 *            发件人
	 * @param password
	 *            发件人密码
	 * @param to
	 *            收件人
	 * @param cc
	 *            抄送人
	 * @param subject
	 *            主题
	 * @param context
	 *            内容
	 * @return true 发送成功 false 发送失败
	 */
	public static boolean sendMail(String from, String password, String[] to, String[] cc, String subject,
			String context) {
		return sendMail(from, password, to, cc, subject, context, null);
	}

	public static void main(String[] args) {
		sendMail(User, Password, "skyfervor".split(";"), "".split(";"), "Test", "张书豪测试");
	}

}
