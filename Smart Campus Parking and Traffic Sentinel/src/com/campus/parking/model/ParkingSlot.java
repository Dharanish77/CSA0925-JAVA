package com.campus.parking.model;

public class ParkingSlot {

    private int slotId;
    private int zoneId;
    private String slotNumber;
    private String slotStatus;

    public ParkingSlot() {
    }

    public ParkingSlot(
            int slotId,
            int zoneId,
            String slotNumber,
            String slotStatus
    ) {
        this.slotId = slotId;
        this.zoneId = zoneId;
        this.slotNumber = slotNumber;
        this.slotStatus = slotStatus;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(String slotStatus) {
        this.slotStatus = slotStatus;
    }

    @Override
    public String toString() {
        return slotNumber + " - " + slotStatus;
    }
}