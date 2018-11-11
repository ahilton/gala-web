package com.zut.galaweb.donation;

import com.zut.galaweb.dto.Donation;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FundCache {

    private final List<Donation> donations = new CopyOnWriteArrayList<>();

    private final AtomicInteger currentDonationTotal = new AtomicInteger(0);

    private final AtomicInteger fundraisingTarget = new AtomicInteger(10000);

    private final AtomicInteger takings = new AtomicInteger(0);


    public void updateDonations(MutableList<Donation> donations) {
        this.donations.clear();
        this.donations.addAll(donations);
    }

    public void updateCurrentDonationTotal(int newValue) {
        this.currentDonationTotal.set(newValue);
    }

    public void updateFundraisingTarget(int target) {
        this.fundraisingTarget.set(target);
    }

    public void updateTakings(int newValue) {
        this.takings.set(newValue);
    }

    public List<Donation> getDonations(){
        return Lists.mutable.ofAll(donations);
    }

    public int getCurrentDonationTotal() {
        return currentDonationTotal.get();
    }

    public int getFundraisingTarget() {
        return fundraisingTarget.get();
    }

    public int getTakings() {
        return takings.get();
    }
}
