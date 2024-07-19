package com.noodle.reference_tag.util;

import com.noodle.reference_tag.dto.TagDto;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagSelectionManager {
    private final List<TagDto> selectedTags = new ArrayList<>();

    public boolean addTag(TagDto tag) {
        if (!selectedTags.contains(tag)) {
            selectedTags.add(tag);
            return true;
        }
        else{
            return false;
        }
    }

    public void removeTag(TagDto tag) {
        selectedTags.remove(tag);
    }

    public List<TagDto> getSelectedTags() {
        return new ArrayList<>(selectedTags);
    }

    public List<Long> getSelectedTagIds() {
        return selectedTags.stream()
                .map(TagDto::getId)
                .collect(Collectors.toList());
    }

    public void clearSelection() {
        selectedTags.clear();
    }
}