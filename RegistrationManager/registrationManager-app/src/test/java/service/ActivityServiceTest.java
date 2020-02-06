package service;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.skyfervor.rm.service.ActivityService;
import com.skyfervor.rm.vo.ActivityVo;

public class ActivityServiceTest {
	private ApplicationContext ctx;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("context/common.xml");
	}

	@Test
	public void test() throws Exception {
		ActivityService activityService = (ActivityService) ctx.getBean("activityService");
		ActivityVo activity = new ActivityVo();
		activity.setName("test");
		activity.setType(56);
		activity.setPublisherId(1L);
		activity.setIsAllSelect((byte) 1);
		activity.setMinimum(1);
		activity.setMaximum(10);
		activity.setDescription("test");
		activity.setBeginTime(new Date());
		activity.setEndTime(new Date());
		activity.setActivityTime(new Date());
		activity.setStatus(55);
		activity.setEnumDataEntityStatus((byte) 0);
		activity.setCreateOperator("test");
		activity.setCreateOperatorId(-1L);
		activity.setCreateTime(new Date());
		activity.setLastUpdateOperator("test");
		activity.setLastUpdateOperatorId(-1L);
		activity.setLastUpdateTime(new Date());
		activityService.insert(activity);
	}

}
