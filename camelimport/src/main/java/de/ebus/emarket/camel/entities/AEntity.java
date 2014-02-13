package de.ebus.emarket.camel.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//import javax.persistence.Version;

/**
 * The Class AEntity is an abstract mapped superclass and comprises all the
 * attributes witch needed in all other entitys.
 * 
 * @author Liepert Philipp
 */
@MappedSuperclass
public abstract class AEntity implements Serializable {

	@Id()
	@GeneratedValue
	@Column(nullable = false)
	private long id;
	private boolean removed;
	/*
	 * @Version private long versionId;
	 */

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8095334309018497207L;

	public AEntity() {
		super();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		if (((AEntity) obj).getId() != getId()) {
			return false;
		}
		return true;
	}

	public long getId() {
		return id;
	}

	public boolean isRemoved() {
		return removed;
	}

	protected void setId(final long id) {
		this.id = id;
	}

	/*
	 * public long getVersionId() { return versionId; }
	 * 
	 * public void setVersionId(final long versionId) { this.versionId =
	 * versionId; }
	 */

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}
}
