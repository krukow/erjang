/**
 * This file is part of Erjang - A JVM-based Erlang VM
 *
 * Copyright (c) 2009 by Trifork
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/


package erjang;

import kilim.Pausable;

/**
 * This is a PID on this node
 */
public class EInternalPID extends EPID {

	private final EProc proc;

	public EInternalPID(EProc self) {
		this.proc = self;
	}
	
	/* (non-Javadoc)
	 * @see erjang.EHandle#self()
	 */
	@Override
	ETask<?> task() {
		return proc;
	}
	
	@Override
	public void send(EObject msg) throws Pausable {
		proc.mbox_send(msg);
	}
	
	/* (non-Javadoc)
	 * @see erjang.EPID#compare_same(erjang.EObject)
	 */
	@Override
	int compare_same(EObject rhs) {
		return rhs.r_compare_same(this);
	}

	/* (non-Javadoc)
	 * @see erjang.EObject#r_compare_same(erjang.EInternalPID)
	 */
	@Override
	int r_compare_same(EInternalPID lhs) {
		return proc.id - lhs.proc.id;
	}
	
	/* (non-Javadoc)
	 * @see erjang.EPID#send_exit(erjang.EPID, erjang.EObject)
	 */
	@Override
	public void exit_signal(EHandle from, EObject reason) throws Pausable {
		proc.send_exit(from, reason);
	}

	/* (non-Javadoc)
	 * @see erjang.EHandle#link_oneway(erjang.EHandle)
	 */
	@Override
	public void link_oneway(EHandle other) {
		proc.link_oneway(other);
	}

	/* (non-Javadoc)
	 * @see erjang.EPID#set_group_leader(erjang.EPID)
	 */
	@Override
	public void set_group_leader(EPID group_leader) {
		proc.set_group_leader(group_leader);
	}

	/**
	 * @return
	 */
	public int internal_pid_number() {
		throw new NotImplemented();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PID<" + task() + ">";
	}
}
