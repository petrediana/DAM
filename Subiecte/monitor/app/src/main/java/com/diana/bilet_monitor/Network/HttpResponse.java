package com.diana.bilet_monitor.Network;

import com.diana.bilet_monitor.Clase.Monitor;

import java.util.List;

public class HttpResponse {
    private List<Monitor> monitors;

    public HttpResponse(List<Monitor> monitors) {
        this.monitors = monitors;
    }

    public List<Monitor> getMonitors() {
        return monitors;
    }

    public void setMonitors(List<Monitor> monitors) {
        this.monitors = monitors;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "monitors=" + monitors +
                '}';
    }
}
