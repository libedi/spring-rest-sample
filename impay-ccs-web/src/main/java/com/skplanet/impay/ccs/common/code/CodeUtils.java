package com.skplanet.impay.ccs.common.code;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeUtils {
	private static final Logger logger = LoggerFactory.getLogger(CodeUtils.class);

	public static void setCodeDisplayNameByAnnotation(final Class<?> domainCodeDetail, final Code instance) {
		final Field[] fields = domainCodeDetail.getFields();
		try {
			for (final Field field : fields) {
				final Annotation[] annotations = field.getAnnotations();
				for (final Annotation annotation : annotations) {
					logger.debug(annotation.toString());
					if (annotation instanceof CodeDisplayName) {
						final CodeDisplayName annotationCodeDisplayName = (CodeDisplayName) annotation;
						final Code fieldClass = (Code) field.get(instance);
						if (fieldClass != null) {
							fieldClass.setCodeDisplayName(annotationCodeDisplayName.codeDisplayName());
						} else {
							instance.setCodeDisplayName(annotationCodeDisplayName.codeDisplayName());
						}
					}
				}

			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Code getCodeClass(final  Class<?> domainClass, final int code) {
		@SuppressWarnings("unchecked")
		final Enum[] enumsInDomain = ((Class<Enum>)domainClass).getEnumConstants();
		if (enumsInDomain == null) {
			return null;
		}

		for(final Enum<?> e : enumsInDomain) {
			if (e instanceof Code) {
				final Code domainCode = (Code) e;
				if (domainCode.getCode()==code) {
					return domainCode;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static String getCodeDisplayName(final  Class<?> domainClass, final int code) {
		@SuppressWarnings("unchecked")
		final Enum[] enumsInDomain = ((Class<Enum>)domainClass).getEnumConstants();
		if (enumsInDomain == null) {
			return null;
		}

		for(final Enum<?> e : enumsInDomain) {
			if (e instanceof Code) {
				final Code domainCode = (Code) e;
				if (domainCode.getCode()==code) {
					return domainCode.getCodeDisplayName();
				}
			}
		}
		return null;
	}


	@SuppressWarnings({"rawtypes", "unchecked"})
	public static String getCodeDisplayName(final String domainCodeEnumClassName, final int code) throws ClassNotFoundException {
		final Class<Enum> domainClass = (Class<Enum>)Class.forName(domainCodeEnumClassName);

		return getCodeDisplayName(domainClass, code);
	}

	public static List<String> getClassNameListInPackageByJarUrl  (final URL packageUrl, final String packageName) throws FileNotFoundException, IOException {
		final JarInputStream jarFile = new JarInputStream (packageUrl.openStream());

		return getClassNameListFromJarInputStream(packageName,  jarFile);
	}


	public static List<String> getClassNameListInPackageByJar  (final String jarFilePath, final String packageName) throws FileNotFoundException, IOException {
		final JarInputStream jarFile = new JarInputStream (new FileInputStream (jarFilePath));

		return getClassNameListFromJarInputStream(packageName,  jarFile);
	}


	private static List<String> getClassNameListFromJarInputStream(final String packageName,	final JarInputStream jarFile) throws IOException {
		final ArrayList<String> classNameList = new ArrayList<String>();

		while (true) {
			try {
				final JarEntry jarEntry= jarFile.getNextJarEntry ();
				final String classPatternName = jarEntry.getName().replaceAll("/", "\\.");
				logger.debug("Found File in JarFile  " + classPatternName);

				if ((classPatternName.startsWith (packageName)) &&	(jarEntry.getName ().endsWith (".class")) ) {
					final String jarEntryName = classPatternName.replace(".class", "").replaceAll(packageName +".", "");

					logger.debug("    ---- Domain ClassName: " +jarEntryName);
					classNameList.add (jarEntryName);
				}
			}  catch (final NullPointerException e) {
				//e.printStackTrace();
				break;
			}
		}
		return classNameList;
	}

}
