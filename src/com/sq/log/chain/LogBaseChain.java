package com.sq.log.chain;

import com.sq.log.result.LogResultInfo;

public abstract class LogBaseChain {

	private LogBaseChain nextChain;

	/**
	 * @return the nextChain
	 */
	public LogBaseChain getNextChain() {
		return nextChain;
	}

	/**
	 * @param nextChain the nextChain to set
	 */
	public void setNextChain(LogBaseChain nextChain) {
		this.nextChain = nextChain;
	}
	
	private LogResultInfo data;
	
	public abstract void execute();

	public LogResultInfo getData() {
		return data;
	}

	public void setData(LogResultInfo data) {
		this.data = data;
	}
}
