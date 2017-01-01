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

import org.libresonic.player.domain.AvatarScheme;
import org.libresonic.player.domain.User;
import org.libresonic.player.domain.UserSettings;
import org.libresonic.player.service.SecurityService;
import org.libresonic.player.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/gettingStarted")
public class GettingStartedController {

    @Autowired
    private SettingsService settingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView gettingStarted(HttpServletRequest request) {
        ControllerUtils.updatePortAndContextPath(request,settingsService);

        if (request.getParameter("hide") != null) {
            settingsService.setGettingStartedEnabled(false);
            settingsService.save();
            return new ModelAndView(new RedirectView("home.view"));
        }

        Map<String, Object> map = new HashMap<>();;
        map.put("runningAsRoot", "root".equals(System.getProperty("user.name")));
        return new ModelAndView("gettingStarted", "model", map);
    }

}
