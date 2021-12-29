package com.company.clinic.command;

import com.company.clinic.model.visit.VisitAction;
import com.company.clinic.validation.visit.VisitExistsAndHasProperStatus;

import javax.validation.constraints.NotNull;
import java.util.Objects;


@VisitExistsAndHasProperStatus
public class VisitActionCommand {

    @NotNull
    private long visitId;

    private VisitAction actionType;

    public VisitActionCommand() {
    }

    public VisitActionCommand(long visitId, VisitAction actionType) {
        this.visitId = visitId;
        this.actionType = actionType;
    }

    public long getVisitId() {
        return visitId;
    }

    public VisitAction getActionType() {
        return actionType;
    }

    public void setActionType(VisitAction actionType) {
        this.actionType = actionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitActionCommand that = (VisitActionCommand) o;
        return visitId == that.visitId && Objects.equals(actionType, that.actionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitId, actionType);
    }
}
