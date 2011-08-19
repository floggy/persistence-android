package org.floggy.persistence.android.sample;

import java.util.UUID;

import org.floggy.persistence.android.Persistable;

public class Agent implements Persistable {

	protected String uid;
	protected long valid;

	public Agent() {
		uid = UUID.randomUUID().toString();
		valid = System.currentTimeMillis();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getValid() {
		return valid;
	}

	public void setValid(long valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return "Agent [uid=" + uid + ", valid=" + valid + "]";
	}

}
