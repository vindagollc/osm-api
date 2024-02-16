package com.vindago.osmapi.map.data

import nl.adaptivity.xmlutil.serialization.XmlSerialName


interface Element {
    @XmlSerialName("id")
    val id: Long

}

data class Node(override val id: Long, val version: Int): Element  {

    
}

