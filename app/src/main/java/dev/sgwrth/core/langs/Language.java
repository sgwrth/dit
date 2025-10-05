package dev.sgwrth.core.langs;

public sealed interface Language
        permits LangC, LangCpp, LangJava {
    String getExtension();
}
