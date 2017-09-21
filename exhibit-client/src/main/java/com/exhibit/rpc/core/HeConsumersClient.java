package com.exhibit.rpc.core;

import com.exhibit.rpc.model.HessianConsumers;
import com.exhibit.rpc.util.ConstantsUtil;
/**
 * @author: ankang
 */
public class HeConsumersClient implements HeClientInterface {

	private HeClientActualize<HessianConsumers> actualize;

	private HeConsumersClient(HeClientActualize<HessianConsumers> actualize) {
		this.actualize = actualize;
	}

	private static HeConsumersClient INSTANCE = null;
	public static HeConsumersClient getInstance(
			HeClientActualize<HessianConsumers> actualize) {
		if (null == INSTANCE) {
			synchronized (HeConsumersClient.class) {
				if (null == INSTANCE) {
					INSTANCE = new HeConsumersClient(actualize);
				}
			}
		}
		return INSTANCE;
	}

	public void addConsumer(HessianConsumers con) {

		actualize.addEntity(con);
	}

	@Override
	public void register() throws Exception {

		actualize.register(ConstantsUtil.HE_REGISTER_CONSUMER);
	}

	@Override
	public void destroy() throws Exception {

		actualize.destroy(ConstantsUtil.HE_DESTROY_CONSUMER);
	}
}
