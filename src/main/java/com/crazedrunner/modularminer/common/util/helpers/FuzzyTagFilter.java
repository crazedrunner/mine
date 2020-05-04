package com.crazedrunner.modularminer.common.util.helpers;

import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuzzyTagFilter {
    private boolean whitelist;
    private HashSet<String> filterTags;

    public FuzzyTagFilter(String[] filterTags, boolean whitelist){
        this.filterTags = new HashSet<String>(Arrays.asList(filterTags));
        this.whitelist = whitelist;
    }

    public FuzzyTagFilter(String[] filterTags){
        this(filterTags, true);
    }

    public FuzzyTagFilter(boolean whitelist){
        this(new String[]{}, whitelist);
    }

    public FuzzyTagFilter(){
        this(new String[]{}, true);
    }

    boolean evaluate(ResourceLocation tagIn){
        boolean match = false;

        for (String filterTag : filterTags) {
            Pattern p = Pattern.compile(filterTag);
            Matcher m = p.matcher(tagIn.toString());
            match = m.find();
            if(match){
                break;
            }
        }
        return match == whitelist;
    }

    public void addFilter(String filter){
        this.filterTags.add(filter);
    }

    public void removeFilter(String filter){
        this.filterTags.remove(filter);
    }

    public String[] toArray(){
        return (String[])this.filterTags.toArray();
    }

    public boolean isWhitelist(){
        return whitelist;
    }

    public boolean isBlacklist(){
        return !whitelist;
    }
}
