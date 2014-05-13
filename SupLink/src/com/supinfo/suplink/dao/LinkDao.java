package com.supinfo.suplink.dao;

import java.util.List;

import com.supinfo.suplink.entity.Link;

public interface LinkDao {
	public void addLink(Link link);
	public void removeLink(long linkId);
	public Link getLinkById(long linkId);
	public List<Link> getLinksByUserId(long userId);
	public String createShortenedUrl();
	public Link getOriginalLink(String shortenedUrl);
	public void updateLink(Link link);
}
