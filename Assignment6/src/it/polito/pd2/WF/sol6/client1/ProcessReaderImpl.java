package it.polito.pd2.WF.sol6.client1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.polito.pd2.WF.ActionStatusReader;
import it.polito.pd2.WF.ProcessReader;
import it.polito.pd2.WF.WorkflowReader;
import it.polito.pd2.WF.sol6.client1.gen.ActionStatusType;
import it.polito.pd2.WF.sol6.client1.gen.ProcessSummary;

public class ProcessReaderImpl implements ProcessReader {

	private WorkflowReaderImpl workflow;
	private Calendar startTime;
	private List<ActionStatusReader> status;
	
	public ProcessReaderImpl(ProcessSummary process, WorkflowReaderImpl workflow) {
		this.workflow=workflow;
		this.startTime=process.getStartTime().toGregorianCalendar();
		
		this.status=new ArrayList<ActionStatusReader>();
		for(ActionStatusType status : process.getActionStatus())
			this.status.add(new ActionStatusReaderImpl(status));
	}
	
	@Override
	public Calendar getStartTime() {
        return startTime;
    }

	@Override
    public List<ActionStatusReader> getStatus() {
        if (status == null) {
            status = new ArrayList<ActionStatusReader>();
        }
        return status;
    }

	@Override
	public WorkflowReader getWorkflow() {
		return workflow;
	}

}
