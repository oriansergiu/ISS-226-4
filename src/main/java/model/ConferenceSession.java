package model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ConferenceSession")
public class ConferenceSession {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(precision = 15, scale = 5)
    private BigDecimal feeValue;

    private Date startDate;

    private Date endDate;

    private Date abstractDeadline;

    private Date proposalDeadline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getFeeValue() {
        return feeValue;
    }

    public void setFeeValue(BigDecimal feeValue) {
        this.feeValue = feeValue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getAbstractDeadline() {
        return abstractDeadline;
    }

    public void setAbstractDeadline(Date abstractDeadline) {
        this.abstractDeadline = abstractDeadline;
    }

    public Date getProposalDeadline() {
        return proposalDeadline;
    }

    public void setProposalDeadline(Date proposalDeadline) {
        this.proposalDeadline = proposalDeadline;
    }
}
