/*
 * MIT License
 *
 * Copyright (c) 2024 Andrei F._
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.might.instancecontroller.controllers;

import org.might.instancecontroller.services.KeystoneService;
import org.might.instancecontroller.services.transport.impl.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static org.might.instancecontroller.utils.SettingsHelper.OS_TOKEN;
import static org.might.instancecontroller.utils.SettingsHelper.TIMEOUT;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final KeystoneService keystoneService;

    @Autowired
    public AuthController(KeystoneService keystoneService) {
        this.keystoneService = keystoneService;
    }

    @GetMapping("/token")
    public Object auth(HttpServletResponse response) {
        RestResponse restResponse = keystoneService.authenticate();
        response.addHeader(OS_TOKEN, restResponse.getHeaders().getFirst(OS_TOKEN));
        response.addHeader(TIMEOUT, restResponse.getHeaders().getFirst(TIMEOUT));
        return restResponse.getStringEntity();
    }

    @GetMapping
    public String initData(Model model) {
        return "Hello, world!";
    }

}
