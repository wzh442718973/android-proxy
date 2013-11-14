package com.lechucksoftware.proxy.proxysettings.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lechucksoftware.proxy.proxysettings.R;
import com.lechucksoftware.proxy.proxysettings.db.DBTag;
import com.lechucksoftware.proxy.proxysettings.utils.UIUtils;

import java.util.List;

/**
 * Created by marco on 12/09/13.
 */
public class TagsView extends LinearLayout
{
    private LinearLayout singleLineTagsContainer;
    private FlowLayout multipleLineTagsContainer;

    private ViewGroup getEnabledContainer()
    {
        if (singleLine == true)
        {
            return singleLineTagsContainer;
        }
        else
        {
            return multipleLineTagsContainer;
        }
    }

    private boolean singleLine;

    public TagsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        readStyleParameters(context,attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null)
        {
            if (singleLine == true)
            {
                View v = inflater.inflate(R.layout.tags_singleline, this);
                singleLineTagsContainer = (LinearLayout) v.findViewById(R.id.tags_container);
            }
            else
            {
                View v = inflater.inflate(R.layout.tags_multiline, this);
                multipleLineTagsContainer = (FlowLayout) v.findViewById(R.id.tags_container);
            }
        }
    }

    private void readStyleParameters(Context context, AttributeSet attributeSet)
    {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.TagsView);
        try
        {
            singleLine = a.getBoolean(R.styleable.TagsView_singleLine, false);
        }
        finally
        {
            a.recycle();
        }
    }

    public void setTags(List<DBTag> tags)
    {
        if (getEnabledContainer() != null)
        {
            getEnabledContainer().removeAllViews();

            if (tags.size() > 0)
            {
                for (DBTag tag : tags)
                {
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    TextView t = (TextView) inflater.inflate(R.layout.tag, getEnabledContainer(), false);
                    t.setBackgroundColor(UIUtils.getTagsColor(getContext(), tag.tagColor));
                    t.setText(tag.tag);
                    getEnabledContainer().addView(t);
                }
            }
        }
    }
}
