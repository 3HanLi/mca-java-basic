package com.wy.mca.designmodel.template.around;

import com.wy.mac.java.designmodel.project.template.rule.itf.IRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 核心逻辑的前后规则处理类
 * 
 * @version 2017-7-19 下午2:37:49
 * @author 王勇
 */
public class AroundProcessor<E> {

	/**
	 * 前规则处理集合
	 */
	private List<IRule<E>> beforeRuleList = new ArrayList<>();

	/**
	 * 后规则处理集合
	 */
	private List<IRule<E>> afterRuleList = new ArrayList<>();

	public void addBeforeRule(IRule<E> rule) {
		beforeRuleList.add(rule);
	}

	public void addAfterRule(IRule<E> rule) {
		afterRuleList.add(rule);
	}

	public E[] before(E[] vos) {
		E[] results = vos;
		for (IRule<E> rule : beforeRuleList) {
			results = rule.process(results);
		}
		return results;
	}

	public E[] after(E[] vos) {
		E[] results = vos;
		for (IRule<E> rule : afterRuleList) {
			results = rule.process(results);
		}
		return results;
	}
}
