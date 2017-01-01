/*
 This file is part of Libresonic.

 Libresonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Libresonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Libresonic.  If not, see <http://www.gnu.org/licenses/>.

 Copyright 2016 (C) Libresonic Authors
 Based upon Subsonic, Copyright 2009 (C) Sindre Mehus
 */
package org.libresonic.player.controller;

import org.libresonic.player.domain.MediaFile;
import org.libresonic.player.service.MediaFileService;
import org.libresonic.player.service.RatingService;
import org.libresonic.player.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for updating music file ratings.
 *
 * @author Sindre Mehus
 */
@Controller
@RequestMapping("/setRating")
public class SetRatingController {

    @Autowired
    private RatingService ratingService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MediaFileService mediaFileService;


    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView handleRequestInternal(HttpServletRequest request) throws Exception {
        int id = ServletRequestUtils.getRequiredIntParameter(request, "id");
        Integer rating = ServletRequestUtils.getIntParameter(request, "rating");
        if (rating == 0) {
            rating = null;
        }
        MediaFile mediaFile = mediaFileService.getMediaFile(id);
        String username = securityService.getCurrentUsername(request);
        ratingService.setRatingForUser(username, mediaFile, rating);

        return new ModelAndView(new RedirectView("main.view?id=" + id));
    }
}
