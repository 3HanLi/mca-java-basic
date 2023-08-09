package com.mca.concurrent.container.queue.blocked;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class DelayedElement implements Delayed {

	private final long delay; // 延迟时间
	private final long expire; // 到期时间
	private final String msg; // 数据
	private final long now; // 创建时间

	public DelayedElement(long delay, String msg) {
		this.delay = delay;
		this.msg = msg;
		expire = System.currentTimeMillis() + delay; // 到期时间 = 当前时间+延迟时间
		now = System.currentTimeMillis();
	}

	/**
	 * 还需要多长时间达到过期，动态计算
	 *
	 * @param unit
	 * @return
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	/**
	 * 用于延迟队列内部比较排序 当前时间的延迟时间 - 比较对象的延迟时间
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(Delayed o) {
		return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DelayedElement{");
		sb.append("delay=").append(delay);
		sb.append(", expire=").append(expire);
		sb.append(", msg='").append(msg).append('\'');
		sb.append(", now=").append(now);
		sb.append('}');
		return sb.toString();
	}
}
