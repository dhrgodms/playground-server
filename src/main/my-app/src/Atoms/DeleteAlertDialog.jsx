import {
    AlertDialogBody,
    AlertDialogContent,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogOverlay,
    Button, IconButton, useDisclosure, useToast
} from "@chakra-ui/react";
import {DeleteIcon} from "@chakra-ui/icons";
import React, {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

function DeleteAlertDialog({isOpen, onOpen, onClose}) {
    const cancelRef = React.useRef()
    const toast = useToast();
    const navigate = useNavigate();
    const [id,setId] = useState(window.location.href.split('/')[2]);

    function onDeletePost(){


        axios.delete(`http://ok-archive:2023/api/post/delete/${id}`).then(response => {
            console.log(response);
            toast({
                title: "게시글 삭제 완료",
                status: "success",
                duration: 9000,
                isClosable: true,
            });
            navigate(-1);
        }).catch(error=>console.log(error));
    }

    return (
        <>
            <IconButton icon={<DeleteIcon/>} colorScheme="blackAlpha" onClick={onOpen}  aria-label={'delete'}/>

            <DeleteAlertDialog
                isOpen={isOpen}
                leastDestructiveRef={cancelRef}
                onClose={onClose}
            >
                <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                            Delete this Post
                        </AlertDialogHeader>

                        <AlertDialogBody>
                            정말 삭제하시겠습니까?
                        </AlertDialogBody>

                        <AlertDialogFooter>
                            <Button ref={cancelRef} onClick={onClose}>
                                취소
                            </Button>
                            <Button colorScheme='red' onClick={()=>{
                                // onDeletePost();
                                onClose();
                            }} ml={3}>
                                삭제
                            </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                </AlertDialogOverlay>
            </DeleteAlertDialog>
        </>
    )
}

export default DeleteAlertDialog;