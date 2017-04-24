/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.izeye.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Utilities for XML.
 *
 * @author Johnny Lim
 */
public abstract class XmlUtils {

	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final ThreadLocal<DocumentBuilderFactory> DOCUMENT_BUILDER_FACTORY =
			new ThreadLocal<DocumentBuilderFactory>() {
				@Override
				protected DocumentBuilderFactory initialValue() {
					return DocumentBuilderFactory.newInstance();
				}
			};

	private static final ThreadLocal<TransformerFactory> TRANSFORMER_FACTORY =
			new ThreadLocal<TransformerFactory>() {
				@Override
				protected TransformerFactory initialValue() {
					return TransformerFactory.newInstance();
				}
			};

	private XmlUtils() {
	}

	public static Document xml2Document(String xml) {
		try {
			DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.get().newDocumentBuilder();
			return builder.parse(new ByteArrayInputStream(xml.getBytes(DEFAULT_CHARSET)));
		}
		catch (ParserConfigurationException ex) {
			throw new RuntimeException(ex);
		}
		catch (SAXException ex) {
			throw new RuntimeException(ex);
		}
		catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String document2Xml(Document document) {
		StringWriter writer = new StringWriter();
		writeDocumentToStreamResult(document, new StreamResult(writer));
		return writer.toString();
	}

	public static void writeDocumentToOutputStream(Document document, OutputStream outputStream) {
		writeDocumentToStreamResult(document, new StreamResult(outputStream));
	}

	private static void writeDocumentToStreamResult(Document document, StreamResult outputTarget) {
		try {
			Transformer transformer = TRANSFORMER_FACTORY.get().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(document), outputTarget);
		}
		catch (TransformerConfigurationException ex) {
			throw new RuntimeException(ex);
		}
		catch (TransformerException ex) {
			throw new RuntimeException(ex);
		}
	}

}
