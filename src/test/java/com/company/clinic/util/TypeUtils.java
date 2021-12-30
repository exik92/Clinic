package com.company.clinic.util;

import com.company.clinic.dto.PatientDto;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class TypeUtils {

    public static class TestPageResult<T> {
        private List<T> content;
        private int totalElements;
        private int totalPages;
        private boolean last;
        private int size;
        private int number;
        private boolean first;
        private boolean empty;
        private int numberOfElements;
        private TestSort sort;
        private TestPageable pageable;


        public TestPageResult() {

        }

        public List<T> getContent() {
            return content;
        }

        public void setContent(List<T> content) {
            this.content = content;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public TestSort getSort() {
            return sort;
        }

        public void setSort(TestSort sort) {
            this.sort = sort;
        }

        public TestPageable getPageable() {
            return pageable;
        }

        public void setPageable(TestPageable pageable) {
            this.pageable = pageable;
        }

        public static class TestSort {
            private boolean sorted;
            private boolean unsorted;
            private boolean empty;

            public boolean isSorted() {
                return sorted;
            }

            public void setSorted(boolean sorted) {
                this.sorted = sorted;
            }

            public boolean isUnsorted() {
                return unsorted;
            }

            public void setUnsorted(boolean unsorted) {
                this.unsorted = unsorted;
            }

            public boolean isEmpty() {
                return empty;
            }

            public void setEmpty(boolean empty) {
                this.empty = empty;
            }
        }


        public static class TestPageable {
            private TestSort sort;
            private int offset;
            private int pageSize;
            private int pageNumber;
            private boolean paged;
            private boolean unpaged;

            public TestSort getSort() {
                return sort;
            }

            public void setSort(TestSort sort) {
                this.sort = sort;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageNumber() {
                return pageNumber;
            }

            public void setPageNumber(int pageNumber) {
                this.pageNumber = pageNumber;
            }

            public boolean isPaged() {
                return paged;
            }

            public void setPaged(boolean paged) {
                this.paged = paged;
            }

            public boolean isUnpaged() {
                return unpaged;
            }

            public void setUnpaged(boolean unpaged) {
                this.unpaged = unpaged;
            }
        }


    }

}