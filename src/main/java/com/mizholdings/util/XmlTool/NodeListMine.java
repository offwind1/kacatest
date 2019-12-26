package com.mizholdings.util.XmlTool;

import org.w3c.dom.NodeList;

import java.util.Iterator;

public class NodeListMine implements Iterable<ElementMine> {
    private NodeList list;

    public NodeListMine(NodeList list) {
        this.list = list;
    }

    @Override
    public Iterator iterator() {
        return new NodeIteratorMine(list);
    }
}