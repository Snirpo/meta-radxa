DESCRIPTION = "Linux kernel for RockPi-S"

require recipes-kernel/linux/linux-yocto.inc

KERNEL_EXTRA_ARGS_append = " KCFLAGS=-w"

# We need mkimage for the overlays
DEPENDS += "openssl-native u-boot-mkimage-radxa-native"

do_compile[depends] += "u-boot-mkimage-radxa-native:do_populate_sysroot"

SRC_URI = " \
	git://github.com/radxa/kernel.git;branch=stable-4.4-rockpis; \
"

SRCREV = "6b7accbc999b6caa8ef603b9d904c99694d0bf41"
LINUX_VERSION = "4.4.143"

# Override local version in order to use the one generated by linux build system
# And not "yocto-standard"
LINUX_VERSION_EXTENSION = ""
PR = "r1"
PV = "${LINUX_VERSION}"

# Include only supported boards for now
COMPATIBLE_MACHINE = "(rk3036|rk3066|rk3288|rk3328|rk3399|rk3308)"
deltask kernel_configme

FILES_${KERNEL_PACKAGE_NAME}-devicetree_append = " /boot/overlays"

do_compile_append() {
	oe_runmake dtbs
}

do_install_append() {
	install -d ${D}/boot/overlays
	install -m 644 ${WORKDIR}/linux-$(echo ${MACHINE} | tr - _)-standard-build/arch/arm64/boot/dts/rockchip/overlay/* ${D}/boot/overlays
}
