require recipes-bsp/u-boot/u-boot.inc

HOMEPAGE = "https://github.com/radxa/u-boot"
LICENSE = "GPLv2+"
COMPATIBLE_MACHINE = "(px30|rk3036|rk3066|rk3188|rk3288|rk3328|rk3399|rk3308|rk3399pro)"
SECTION = "bootloaders"
DEPENDS = "dtc-native bc-native swig-native bison-native"

do_compile_prepend () {
	export STAGING_INCDIR=${STAGING_INCDIR_NATIVE};
	export STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE};
}

do_compile_append () {
	# copy to default search path
	if [ ${SPL_BINARY} ]; then
		cp ${B}/spl/${SPL_BINARY} ${B}/
	fi
	if [ -f "${B}/boot.cmd" ]; then
		${B}/tools/mkimage -C none -A arm -T script -d ${B}/boot.cmd ${B}/boot.scr
	fi
}

do_deploy_append() {
	cp -a ${B}/tools/mkimage ${DEPLOY_DIR_IMAGE}
	if [ -f "${WORKDIR}/uEnv.txt" ]; then
		install -D -m 644 ${WORKDIR}/uEnv.txt ${DEPLOYDIR}/
	fi
	if [ -f "${B}/boot.scr" ]; then
		install -D -m 644 ${WORKDIR}/boot.scr ${DEPLOYDIR}/
	fi
	if [ -f "${B}/boot.cmd" ]; then
		install -D -m 644 ${WORKDIR}/boot.cmd ${DEPLOYDIR}/
	fi
}
