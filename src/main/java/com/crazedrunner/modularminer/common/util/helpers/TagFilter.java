package com.crazedrunner.modularminer.common.util.helpers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;;
import java.util.HashSet;
import java.util.Set;

public class TagFilter {
    private boolean whiteList;
    private HashSet<ResourceLocation> tags;

    public TagFilter(HashSet<ResourceLocation> tags, boolean whiteList){
        this.tags = tags;
        this.whiteList = whiteList;
    }

    public TagFilter(HashSet<ResourceLocation> tags){
        this(tags, true);
    }

    public TagFilter(){
        this(new HashSet<>(), true);
    }

    public void addTag(ResourceLocation tag){
        if(tag != null) {
            tags.add(tag);
        }
    }

    public void addTag(final String tagIn){
        ResourceLocation tag = ResourceLocation.tryCreate(tagIn);
        if(tag != null){
            this.tags.add(tag);
        }
    }

    public void removeTag(ResourceLocation tag){
        if(!tags.isEmpty() || tag != null){
            tags.remove(tag);
        }
    }

    public ResourceLocation[] getTagArray(){
        return (ResourceLocation[]) tags.toArray();
    }

    public boolean isWhiteList(){
        return whiteList;
    }

    public boolean isBlackList(){
        return !whiteList;
    }

    public boolean evaluate(Set<ResourceLocation> tagsIn){
        boolean v = this.tags.stream().anyMatch(tagsIn::contains);

        return whiteList == v;
    }
}
