
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="resources/js/ajax/news/tag/ajax_tag.js"></script>
<h3 class="title-body" style="text-align: center">
    Manage Tag
</h3>

<button type="button" class="btn btn-success float-left btn-search">
    <a href="create-tag" style="text-decoration: none;color: white">
        <i class="fas fa-plus"></i>
        Create
    </a>
</button>
<!-- TABLE -->
<div class="table-responsive" style="overflow-x:auto;overflow-y: auto">
    <table class="table text-center">
        <thead>
        <tr id="column-tag"  style="font-weight: 600"></tr>
        </thead>
        <tbody id="row-tag"></tbody>
    </table>
</div>
<!-- END_TABLE -->
<div class="pageable">
    <ul class="pagination"></ul>
</div>