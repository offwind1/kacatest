package com.mizholdings.util.XmlTool;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Iterator;

public class NodeIteratorMine implements Iterator<ElementMine> {
    private NodeList list;
    private int index = 0;
    private final int end;

    public NodeIteratorMine(NodeList list) {
        this.list = list;
        end = list.getLength();
    }

    @Override
    public boolean hasNext() {
        return index < end;
    }

    @Override
    public ElementMine next() {
        Element e = (Element) list.item(index++);

        return new ElementMine(e);
    }
}