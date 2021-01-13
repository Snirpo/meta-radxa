SUMMARY = "Systemd service to setup BT for Broadcom Bluetooth chips"
DESCRIPTION = "Load Broadcom Bluetooth Chips Firmware"
SECTION = "devel"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://brcm-btfw-load.service"

inherit systemd

RDEPENDS_${PN} += "bluez5"

do_install() {
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/brcm-btfw-load.service ${D}${systemd_system_unitdir}
}

SYSTEMD_SERVICE_${PN} = "brcm-btfw-load.service"