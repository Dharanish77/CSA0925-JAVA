package com.campus.parking.dao;

import com.campus.parking.model.ParkingSlot;

import java.util.List;

public class SlotTest {

    public static void main(String[] args) {

        SlotDAO dao = new SlotDAO();

        List<ParkingSlot> slots =
                dao.getAllSlots();

        System.out.println(
                "===== CAMPUS PARKING SLOTS ====="
        );

        for (ParkingSlot slot : slots) {

            System.out.println(
                    "Slot: "
                            + slot.getSlotNumber()
                            + " | Zone: "
                            + slot.getZoneId()
                            + " | Status: "
                            + slot.getSlotStatus()
            );
        }

        System.out.println(
                "================================"
        );

        System.out.println(
                "Available: "
                        + dao.getAvailableCount()
        );

        System.out.println(
                "Occupied: "
                        + dao.getOccupiedCount()
        );

        System.out.println(
                "Reserved: "
                        + dao.getReservedCount()
        );
    }
}